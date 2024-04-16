package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.swagger.DepartmentSwagger;
import ro.lavinia.service.DepartmentServiceImpl;
import java.util.Map;
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController implements DepartmentSwagger {
    private final DepartmentServiceImpl departmentServiceImpl;



    @Operation(summary = "Save a new department")
    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody DepartmentDto departmentDto ){
        return departmentServiceImpl.save(departmentDto);
    }

    @Operation(summary = "Get a department by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<?>  getById(@PathVariable("id") Integer existingId) {
        return departmentServiceImpl.getADepartmentById(existingId);
    }

    @Operation(summary = "Get all department.")
    @GetMapping
    public ResponseEntity<?> getAllDepartment() {
        return departmentServiceImpl.getAllDepartment();
    }


    @Operation(summary = "Update department with patch.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDepartmentWithPatch(
            @RequestBody Map<String, Object> updatedDepartment,
            @PathVariable("id") Integer existingId) {
        return departmentServiceImpl.updatePatch(existingId, updatedDepartment);
    }

    @Operation(summary = "Update department with put.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartmentWithPut(
            @RequestBody ro.lavinia.entity.Department updatedDepartment ,
            @PathVariable("id") Integer existingId) {
        return departmentServiceImpl.updatePut(existingId, updatedDepartment);
    }

    @Operation(summary = "Delete the department by id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return departmentServiceImpl.deleteById(id);
    }

}
