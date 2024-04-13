package ro.lavinia.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.Project;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.mapper.ProjectMapper;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.ProjectRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl {

    @Autowired
    private final ProjectRepository projectRepository;

    @Autowired
    private  final EmployeeRepository employeeRepository;
    public void save(ProjectDto projectDto, Integer employeeId, Integer anotherEmployeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        Employee anotherEmployee = employeeRepository.findById(anotherEmployeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + anotherEmployeeId));


        Project project = ProjectMapper.INSTANCE.ProjectDtoToProjectEntity(projectDto);


        project.getTeamMembers().add(employee);
        project.getTeamMembers().add(anotherEmployee);


        projectRepository.save(project);
    }



    public Optional<ProjectDto> getAProjectById(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return projectOptional.map(ProjectMapper.INSTANCE::ProjectEntityToProjectDto);
        }else {
            throw new RuntimeException("The id for employee is not valid");
        }
    }
    public void deleteById(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for project is not valid");
        }
    }

    public List<ProjectDto> getAllProject() {
        try {
            return projectRepository.findAll().stream()
                    .map(ProjectMapper.INSTANCE::ProjectEntityToProjectDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of project: " + e.getMessage());
        }
    }


    public void updatePatch(Integer existingId, Map<String, Object> updates) {
        var projectOptional = projectRepository.findById(existingId);
        if (projectOptional.isEmpty()) {
            throw new RuntimeException("Project NOT Found");
        }
        Project project = projectOptional.get();
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            switch (entry.getKey()) {
                case "description":
                    project.setDescription((String) entry.getValue());
                    break;
                case "name":
                    project.setName((String) entry.getValue());
                    break;
                case "budget":
                        project.setBudget((Double) entry.getValue());
                    break;
                case "status":
                        project.setStatus((String) entry.getValue());
                    break;
                case "startDate":
                        project.setStartDate((LocalDate) entry.getValue());
                    break;
                case "endDate":
                        project.setEndDate((LocalDate) entry.getValue());
                    break;
                default:
                    throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
            }
        }
        projectRepository.save(project);
    }

    public void updatePut(Integer existingId, Project updatedProject) {
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

        projectRepository.save(existingProject);
    }
}
