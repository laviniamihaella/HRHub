package ro.lavinia.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.mapper.JobPositionMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.io.FileNotFoundException;
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
        if (departmentRepository.findById(departmentId).isEmpty()){
            throw  new RuntimeException("Department not found");
        }
        JobPosition jobPosition = JobPositionMapper.INSTANCE.JobPositionDtoToJobPositionEntity(jobPositionDto);
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






    public void updatePatch(Integer existingId, Map<String, Object> JobPosition) {
        JobPosition existingJobPosition = jobPositionRepository.findById(existingId)
                .orElseThrow(() -> new EntityNotFoundException("JobPosition not found"));

        JobPosition.forEach((key, value) -> {
            switch (key) {
                case "Name":
                    if (value instanceof Date) {
                        existingJobPosition.setName(existingJobPosition.getName());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "Description":
                    if (value instanceof String) {
                        existingJobPosition.setDescription(existingJobPosition.getDescription());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "Requests":
                    if (value instanceof String) {
                        existingJobPosition.setRequests(existingJobPosition.getRequests());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "Responsibilities":
                    if (value instanceof String) {
                        existingJobPosition.setResponsibilities(existingJobPosition.getResponsibilities());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        jobPositionRepository.save(existingJobPosition);
    }

    public void updatePut(Integer existingId, JobPositionDto jobPositionDto) {
        var JobPositionOptional = jobPositionRepository.findById(existingId);
        if (JobPositionOptional.isEmpty()) {
            throw new RuntimeException("JobPosition NOT Found");
        }
        JobPosition existingJobPosition = JobPositionOptional.get();
        existingJobPosition.setName(jobPositionDto.getName());
        existingJobPosition.setDescription(jobPositionDto.getDescription());
        existingJobPosition.setRequests(jobPositionDto.getRequests());
        existingJobPosition.setResponsibilities(jobPositionDto.getResponsibilities());
        jobPositionRepository.save(existingJobPosition);
    }
}
