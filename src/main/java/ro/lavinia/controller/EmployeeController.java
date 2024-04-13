package ro.lavinia.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Employee;
import ro.lavinia.interfacesForSwagger.EmployeeForSwagger;
import ro.lavinia.service.EmployeeServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController implements EmployeeForSwagger {
    private final EmployeeServiceImpl employeeServiceImpl;


    @Operation(summary = "Save a new employee")
    @PostMapping("/create/{departmentId}/{jobPositionId}")
    public void createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Integer departmentId, @PathVariable Integer jobPositionId){
        employeeServiceImpl.save(employeeDto,departmentId,jobPositionId);
    }

    @Operation(summary = "Get a employee by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<EmployeeDto> getById(@PathVariable Integer id) {
        return employeeServiceImpl.getAEmployeeById(id);
    }

    @Operation(summary = "Get all employee.")
    @GetMapping("/all-employee")
    public List<EmployeeDto> getAllEmployee() {
        return employeeServiceImpl.getAllEmployee();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/update-employee-patch/{id}")
    public void updateEmployeeWithPatch(
            @RequestBody Map<String, Object> Employee,
            @PathVariable("id") Integer existingId) {
        employeeServiceImpl.updatePatch(existingId, Employee);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/update-employee-put/{id}")
    public void updateEmployeeWithPut(
            @RequestBody Employee updatedEmployee ,
            @PathVariable("id") Integer existingId) {
        employeeServiceImpl.updatePut(existingId, updatedEmployee);
    }

    @Operation(summary = "Delete the employee by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
        employeeServiceImpl.deleteById(id);
    }
}
