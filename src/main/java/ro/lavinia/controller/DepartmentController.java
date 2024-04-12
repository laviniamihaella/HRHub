package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.interfacesForSwagger.DepartmentForSwagger;
import ro.lavinia.service.DepartmentServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController implements DepartmentForSwagger {
    private final DepartmentServiceImpl departmentServiceImpl;



    @Operation(summary = "Save a new department")
    @PostMapping("/create")
    public void createDepartment(@RequestBody DepartmentDto departmentDto ){
        departmentServiceImpl.save(departmentDto);
    }

    @Operation(summary = "Get a department by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<DepartmentDto> getById(@PathVariable Integer id) {
        return departmentServiceImpl.getADepartmentById(id);
    }

    @Operation(summary = "Get all department.")
    @GetMapping("/all-attendance")
    public List<DepartmentDto> getAllDepartment() {
        return departmentServiceImpl.getAllDepartment();
    }


    @Operation(summary = "Update department with patch.")
    @PatchMapping("/update-attendance-patch/{id}")
    public void updateDepartmentWithPatch(
            @RequestBody Map<String, Object> property,
            @PathVariable("id") Integer existingId) {
        departmentServiceImpl.updatePatch(existingId, property);
    }

    @Operation(summary = "Update department with put.")
    @PutMapping("/update-attendance-put/{id}")
    public void updateDepartmentWithPut(
            @RequestBody DepartmentDto departmentDto ,
            @PathVariable("id") Integer existingId) {
        departmentServiceImpl.updatePut(existingId, departmentDto);
    }

    @Operation(summary = "Delete the department by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
        departmentServiceImpl.deleteById(id);
    }

}
