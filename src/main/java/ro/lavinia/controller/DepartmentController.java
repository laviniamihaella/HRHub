package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.swagger.DepartmentSwagger;
import ro.lavinia.service.DepartmentServiceImpl;
import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController implements DepartmentSwagger {
    private final DepartmentServiceImpl departmentServiceImpl;



    @Operation(summary = "Save a new department")
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto departmentDto ){
                 return departmentServiceImpl.save(departmentDto);
    }

    @Operation(summary = "Get a department by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?>  getById(@PathVariable("id") Integer existingId) {
        return departmentServiceImpl.getADepartmentById(existingId);
    }

    @Operation(summary = "Get all department.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllDepartment() {
        return departmentServiceImpl.getAllDepartment();
    }


    @Operation(summary = "Update department with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateDepartmentWithPatch(
            @RequestBody Map<String, Object> updatedDepartment,
            @PathVariable("id") Integer existingId) {
        return departmentServiceImpl.updatePatch(existingId, updatedDepartment);
    }

    @Operation(summary = "Update department with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateDepartmentWithPut(
            @RequestBody DepartmentDto updatedDepartment ,
            @PathVariable("id") Integer existingId) {
        return departmentServiceImpl.updatePut(existingId, updatedDepartment);
    }

    @Operation(summary = "Delete the department by id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return departmentServiceImpl.deleteById(id);
    }

}
