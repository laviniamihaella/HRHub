package ro.lavinia.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.Project;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.exception.InvalidPeriodException;
import ro.lavinia.mapper.ProjectMapper;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    public ResponseEntity<?> save(ProjectDto projectDto, Integer employeeId, Integer anotherEmployeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

            Employee anotherEmployee = employeeRepository.findById(anotherEmployeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + anotherEmployeeId));

            if (projectDto.getBudget() == null ||
                    projectDto.getStartDate() == null ||
                    projectDto.getEndDate() == null ||
                    projectDto.getName() == null ||
                    projectDto.getName().isEmpty() ||
                    projectDto.getDescription() == null ||
                    projectDto.getDescription().isEmpty() ||
                    projectDto.getStatus() == null ||
                    projectDto.getStatus().isEmpty()) {
                return new ResponseEntity<>("Project information is incomplete.", HttpStatus.BAD_REQUEST);
            }


            Project project = ProjectMapper.INSTANCE.ProjectDtoToProjectEntity(projectDto);

            project.getTeamMembers().add(employee);
            project.getTeamMembers().add(anotherEmployee);

            projectRepository.save(project);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the project.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Project has been successfully saved.", HttpStatus.OK);
    }


    public ResponseEntity<?> getAProjectById(Integer existingId) {
        try {
            Optional<Project> projectOptional = projectRepository.findById(existingId);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                ProjectDto projectDto = ProjectMapper.INSTANCE.ProjectEntityToProjectDto(project);
                return new ResponseEntity<>(projectDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Project with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Project with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> getAllProject() {
        try {
            List<Project> projectList = projectRepository.findAll();
            if (projectList.isEmpty()) {
                throw new EntityNotFoundException("Project list  NOT Found.");
            } else {
                List<ProjectDto> projectDtoList = projectList.stream().toList()
                        .stream().map(ProjectMapper.INSTANCE::ProjectEntityToProjectDto)
                        .toList();
                return new ResponseEntity<>(projectDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Project list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedProject) {
        try {
            var projectOptional = projectRepository.findById(existingId);
            if (projectOptional.isEmpty()) {
                throw new RuntimeException("Project NOT Found");
            }
            Project project = projectOptional.get();
            for (Map.Entry<String, Object> entry : updatedProject.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "description":
                        project.setDescription((String) entry.getValue());
                        break;
                    case "name":
                        project.setName((String) entry.getValue());
                        break;
                    case "budget":
                        project.setBudget((Integer) entry.getValue());
                        break;
                    case "status":
                        project.setStatus((String) entry.getValue());
                        break;
                    case "startdate":
                        if (entry.getValue() instanceof String startDateString) {
                            LocalDate startDate = LocalDate.parse(startDateString);
                            project.setStartDate(startDate);
                        } else {
                            throw new InvalidPeriodException("Invalid data format for 'start date' field");
                        }
                        break;
                    case "enddate":
                        if (entry.getValue() instanceof String endDateString) {
                            LocalDate endDate = LocalDate.parse(endDateString);
                            project.setEndDate(endDate);
                        } else {
                            throw new InvalidPeriodException("Invalid data format for 'end date' field");
                        }
                        break;
                    default:
                        throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
                }
            }
            if (project.getStartDate() != null && project.getEndDate() != null &&
                    project.getStartDate().isAfter(project.getEndDate())) {
                throw new InvalidPeriodException("Start date can not be after end date");
            }
            projectRepository.save(project);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Project with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        } catch (InvalidPeriodException e) {
            return new ResponseEntity<>("Start date can not be after end date", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Project with ID " + existingId + " has been successfully updated with patch.", HttpStatus.OK);

    }

    public ResponseEntity<?> updatePut(Integer existingId, ProjectDto updatedProject) {
        try {

            var projectOptional = projectRepository.findById(existingId);
            if (projectOptional.isEmpty()) {
                throw new EntityNotFoundException("Project with ID " + existingId + " not found");
            }
            Project existingProject = projectOptional.get();

            existingProject.setDescription(updatedProject.getDescription());
            existingProject.setName(updatedProject.getName());
            existingProject.setBudget(updatedProject.getBudget());
            existingProject.setStatus(updatedProject.getStatus());
            existingProject.setStartDate(updatedProject.getStartDate());
            existingProject.setEndDate(updatedProject.getEndDate());

            if (existingProject.getBudget() == null ||
                    existingProject.getStartDate() == null ||
                    existingProject.getEndDate() == null ||
                    existingProject.getName() == null ||
                    existingProject.getName().isEmpty() ||
                    existingProject.getDescription() == null ||
                    existingProject.getDescription().isEmpty() ||
                    existingProject.getStatus() == null ||
                    existingProject.getStatus().isEmpty()) {
                return new ResponseEntity<>("Project information is incomplete.", HttpStatus.BAD_REQUEST);
            }
            if (existingProject.getStartDate().isAfter(existingProject.getEndDate())) {
                throw new InvalidPeriodException("Start date can not be after end date");
            }

            projectRepository.save(existingProject);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Project with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        } catch (InvalidPeriodException e) {
            return new ResponseEntity<>("Start date can not be after end date", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Project with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<Project> projectOptional = projectRepository.findById(existingId);
            if (projectOptional.isPresent()) {
                projectRepository.deleteById(existingId);
                return new ResponseEntity<>("Project with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Project with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Project with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }
}
