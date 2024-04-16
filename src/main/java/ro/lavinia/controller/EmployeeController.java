package ro.lavinia.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Employee;
import ro.lavinia.service.EmployeeServiceImpl;
import ro.lavinia.swagger.EmployeeSwagger;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController implements EmployeeSwagger {
    private final EmployeeServiceImpl employeeServiceImpl;

    @Operation(summary = "Save a new employee")
    @PostMapping("/{departmentId}/{jobPositionId}")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Integer departmentId, @PathVariable Integer jobPositionId) {
        return employeeServiceImpl.save(employeeDto, departmentId, jobPositionId);
    }

    @Operation(summary = "Get a employee by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return employeeServiceImpl.getAEmployeeById(existingId);
    }

    @Operation(summary = "Get all employee.")
    @GetMapping
    public ResponseEntity<?> getAllEmployee() {
        return employeeServiceImpl.getAllEmployee();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEmployeeWithPatch(
            @RequestBody Map<String, Object> updatedEmployee,
            @PathVariable("id") Integer existingId) {
        return employeeServiceImpl.updatePatch(existingId, updatedEmployee);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployeeWithPut(
            @RequestBody Employee updatedEmployee,
            @PathVariable("id") Integer existingId) {
        return employeeServiceImpl.updatePut(existingId, updatedEmployee);
    }

    @Operation(summary = "Delete the employee by an id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {
        return employeeServiceImpl.deleteById(existingId);
    }
}
