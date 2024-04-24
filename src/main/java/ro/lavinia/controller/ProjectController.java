package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.ProjectDto;
import ro.lavinia.swagger.ProjectSwagger;
import ro.lavinia.service.ProjectServiceImpl;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
@PreAuthorize("hasRole('ADMIN')")
public class ProjectController implements ProjectSwagger {

    @Autowired
    private final ProjectServiceImpl projectServiceImpl;

    @Operation(summary = "Save a new project")
    @PostMapping("/{employeeId}/{anotherEmployeeId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createProject(@RequestBody ProjectDto projectDto, @PathVariable Integer employeeId, @PathVariable Integer anotherEmployeeId){
        return projectServiceImpl.save(projectDto,employeeId,anotherEmployeeId);
    }

    @Operation(summary = "Get a project by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return projectServiceImpl.getAProjectById(existingId);
    }

    @Operation(summary = "Get all project.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllProject() {
        return projectServiceImpl.getAllProject();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateProjectWithPatch(
            @RequestBody Map<String, Object> project,
            @PathVariable("id") Integer existingId) {
     return projectServiceImpl.updatePatch(existingId, project);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateProjectWithPut(
            @RequestBody ProjectDto updatedProject ,
            @PathVariable("id") Integer existingId) {
      return projectServiceImpl.updatePut(existingId, updatedProject);
    }

    @Operation(summary = "Delete the project by id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {
      return projectServiceImpl.deleteById(existingId);
    }

}
