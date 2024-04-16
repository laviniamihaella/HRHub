package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.mapper.JobPositionMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPositionServiceImpl {

    private final JobPositionRepository jobPositionRepository;
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<?> save(JobPositionDto jobPositionDto, Integer departmentId) {
        try {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new EntityNotFoundException("Department with ID " + departmentId + "  NOT Found."));

            if (jobPositionDto.getName() == null || jobPositionDto.getName().isEmpty() ||
                    jobPositionDto.getDescription() == null || jobPositionDto.getDescription().isEmpty() ||
                    jobPositionDto.getRequests() == null || jobPositionDto.getRequests().isEmpty() ||
                    jobPositionDto.getResponsibilities() == null || jobPositionDto.getResponsibilities().isEmpty()) {
                return new ResponseEntity<>("Job Position information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            JobPosition jobPosition = JobPositionMapper.INSTANCE.JobPositionDtoToJobPositionEntity(jobPositionDto);
            jobPosition.setDepartment(department);
            jobPositionRepository.save(jobPosition);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department with ID " + departmentId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Job Position has been successfully saved.", HttpStatus.OK);
    }

    public ResponseEntity<?> getAJobPositionById(Integer existingId) {
        try {
            Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(existingId);
            if (jobPositionOptional.isPresent()) {
                JobPosition jobPosition = jobPositionOptional.get();
                JobPositionDto jobPositionDto = JobPositionMapper.INSTANCE.JobPositionEntityToJobPositionDto(jobPosition);
                return new ResponseEntity<>(jobPositionDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Job Position with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job Position with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllJobPositions() {
        try {
            List<JobPosition> jobPositionList = jobPositionRepository.findAll();
            if (jobPositionList.isEmpty()) {
                throw new EntityNotFoundException("Job position list  NOT Found.");
            } else {
                List<JobPositionDto> jobPositionDtoList = jobPositionList.stream()
                        .map(JobPositionMapper.INSTANCE::JobPositionEntityToJobPositionDto)
                        .toList();
                return new ResponseEntity<>(jobPositionDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job position list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedJobPosition) {

        try {
            var jobPositionOptional = jobPositionRepository.findById(existingId);
            if (jobPositionOptional.isEmpty()) {
                throw new EntityNotFoundException("Job Position with ID " + existingId + " NOT Found.");
            }
            JobPosition jobPosition = jobPositionOptional.get();

            for (Map.Entry<String, Object> entry : updatedJobPosition.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "name":
                        jobPosition.setName((String) entry.getValue());
                        break;
                    case "description":
                        jobPosition.setDescription((String) entry.getValue());
                        break;
                    case "requests":
                        jobPosition.setRequests((String) entry.getValue());
                        break;
                    case "responsibilities":
                        jobPosition.setResponsibilities((String) entry.getValue());
                        break;
                    default:
                        return new ResponseEntity<>("Invalid field: " + key, HttpStatus.BAD_REQUEST);
                }
            }
            jobPositionRepository.save(jobPosition);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job Position with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Job Position with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }

    public ResponseEntity<?> updatePut(Integer existingId, JobPosition updatedJobPosition) {
        try {

            var JobPositionOptional = jobPositionRepository.findById(existingId);
            if (JobPositionOptional.isEmpty()) {
                throw new EntityNotFoundException("Job Position with ID " + existingId + " NOT Found.");
            }
            JobPosition existingJobPosition = JobPositionOptional.get();

            existingJobPosition.setName(updatedJobPosition.getName());
            existingJobPosition.setDescription(updatedJobPosition.getDescription());
            existingJobPosition.setRequests(updatedJobPosition.getRequests());
            existingJobPosition.setResponsibilities(updatedJobPosition.getResponsibilities());

            if (existingJobPosition.getName() == null || existingJobPosition.getName().isEmpty() ||
                    existingJobPosition.getDescription() == null || existingJobPosition.getDescription().isEmpty() ||
                    existingJobPosition.getRequests() == null || existingJobPosition.getRequests().isEmpty() ||
                    existingJobPosition.getResponsibilities() == null || existingJobPosition.getResponsibilities().isEmpty()) {
                return new ResponseEntity<>("Job Position information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            jobPositionRepository.save(existingJobPosition);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job Position with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Job Position with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<JobPosition> optionalJobPosition = jobPositionRepository.findById(existingId);
            if (optionalJobPosition.isPresent()) {
                jobPositionRepository.deleteById(existingId);
                return new ResponseEntity<>("Job Position with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Job Position with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Job Position with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }
}
