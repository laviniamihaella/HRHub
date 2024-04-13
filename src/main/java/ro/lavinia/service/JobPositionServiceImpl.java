package ro.lavinia.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.mapper.JobPositionMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobPositionServiceImpl {
    
    private final JobPositionRepository jobPositionRepository;
    private final DepartmentRepository departmentRepository;


    public void save(JobPositionDto jobPositionDto,Integer departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("The Department id is not valid."));

        JobPosition jobPosition = JobPositionMapper.INSTANCE.JobPositionDtoToJobPositionEntity(jobPositionDto);

        jobPosition.setDepartment(department);
        jobPositionRepository.save(jobPosition);
    }

    public Optional<JobPositionDto> getAJobPositionById(Integer id) {
        try {
            Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(id);
            return jobPositionOptional.map(JobPositionMapper.INSTANCE::JobPositionEntityToJobPositionDto);
        }catch (Exception e) {
            throw new RuntimeException("Failed to get ann attendance by id: " + e.getMessage());
        }

    }
    public List<JobPositionDto> getAllJobPosition() {
        try {
            return jobPositionRepository.findAll().stream()
                    .map(JobPositionMapper.INSTANCE::JobPositionEntityToJobPositionDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of attendance: " + e.getMessage());
        }
    }
    public void deleteById(Integer id) {
        if (jobPositionRepository.existsById(id)) {
            jobPositionRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for job position is not valid");
        }
    }


    public void updatePatch(Integer existingId, Map<String, Object> updatedJobPosition) {
        var jobPositionOptional = jobPositionRepository.findById(existingId);
        if (jobPositionOptional.isEmpty()) {
            throw new ro.lavinia.exception.EntityNotFoundException("JobPosition NOT Found");
        }
        JobPosition jobPosition = jobPositionOptional.get();

        for (Map.Entry<String, Object> entry : updatedJobPosition.entrySet()) {
            switch (entry.getKey()) {
                case "Name":
                    jobPosition.setName((String) entry.getValue());
                    break;
                case "Description":
                        jobPosition.setDescription((String) entry.getValue());
                    break;
                case "Requests":
                    jobPosition.setRequests((String) entry.getValue());
                    break;
                case "Responsibilities":
                    jobPosition.setResponsibilities((String) entry.getValue());
                    break;
                default:
                    throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
            }
        }

        jobPositionRepository.save(jobPosition);
    }

    public void updatePut(Integer existingId, JobPosition updatedJobPosition) {
        var JobPositionOptional = jobPositionRepository.findById(existingId);
        if (JobPositionOptional.isEmpty()) {
            throw new EntityNotFoundException("Job Position with ID " + existingId + " NOT Found");
        }
        JobPosition existingJobPosition = JobPositionOptional.get();

        existingJobPosition.setName(updatedJobPosition.getName());
        existingJobPosition.setDescription(updatedJobPosition.getDescription());
        existingJobPosition.setRequests(updatedJobPosition.getRequests());
        existingJobPosition.setResponsibilities(updatedJobPosition.getResponsibilities());

        jobPositionRepository.save(existingJobPosition);
    }
}
