package ro.lavinia.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.entity.Project;
import ro.lavinia.interfacesForSwagger.ProjectForSwagger;
import ro.lavinia.service.ProjectServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController implements ProjectForSwagger {

    @Autowired
    private ProjectServiceImpl projectServiceImpl;

    @Operation(summary = "Save a new project")
    @PostMapping("/create/{employeeId}/{anotherEmployeeId}")
    public void createProject(@RequestBody ProjectDto projectDto, @PathVariable Integer employeeId,  @PathVariable Integer anotherEmployeeId){
      projectServiceImpl.save(projectDto,employeeId,anotherEmployeeId);
    }

    @Operation(summary = "Get a project by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<ProjectDto> getById(@PathVariable Integer id) {
        return projectServiceImpl.getAProjectById(id);
    }

    @Operation(summary = "Get all project.")
    @GetMapping("/all-project")
    public List<ProjectDto> getAllProject() {
        return projectServiceImpl.getAllProject();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/update-project-patch/{id}")
    public void updateProjectWithPatch(
            @RequestBody Map<String, Object> project,
            @PathVariable("id") Integer existingId) {
      projectServiceImpl.updatePatch(existingId, project);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/update-project-put/{id}")
    public void updateProjectWithPut(
            @RequestBody Project updatedProject ,
            @PathVariable("id") Integer existingId) {
      projectServiceImpl.updatePut(existingId, updatedProject);
    }

    @Operation(summary = "Delete the project by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
      projectServiceImpl.deleteById(id);
    }

    
    
}
