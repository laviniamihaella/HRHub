package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.swagger.ProjectSwagger;
import ro.lavinia.service.ProjectServiceImpl;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController implements ProjectSwagger {

    @Autowired
    private final ProjectServiceImpl projectServiceImpl;

    @Operation(summary = "Save a new project")
    @PostMapping("/{employeeId}/{anotherEmployeeId}")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto, @PathVariable Integer employeeId, @PathVariable Integer anotherEmployeeId){
        return projectServiceImpl.save(projectDto,employeeId,anotherEmployeeId);
    }

    @Operation(summary = "Get a project by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return projectServiceImpl.getAProjectById(existingId);
    }

    @Operation(summary = "Get all project.")
    @GetMapping
    public ResponseEntity<?> getAllProject() {
        return projectServiceImpl.getAllProject();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProjectWithPatch(
            @RequestBody Map<String, Object> project,
            @PathVariable("id") Integer existingId) {
     return projectServiceImpl.updatePatch(existingId, project);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProjectWithPut(
            @RequestBody ro.lavinia.entity.Project updatedProject ,
            @PathVariable("id") Integer existingId) {
      return projectServiceImpl.updatePut(existingId, updatedProject);
    }

    @Operation(summary = "Delete the project by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {
      return projectServiceImpl.deleteById(existingId);
    }

}
