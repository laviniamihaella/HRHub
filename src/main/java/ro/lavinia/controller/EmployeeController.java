package ro.lavinia.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Employee;
import ro.lavinia.service.EmployeeServiceImpl;
import ro.lavinia.swagger.EmployeeSwagger;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController implements EmployeeSwagger {
    private final EmployeeServiceImpl employeeServiceImpl;

    @Operation(summary = "Save a new employee")
    @PostMapping("/{departmentId}/{jobPositionId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Integer departmentId, @PathVariable Integer jobPositionId) {
        return employeeServiceImpl.save(employeeDto, departmentId, jobPositionId);
    }

    @Operation(summary = "Get a employee by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return employeeServiceImpl.getAEmployeeById(existingId);
    }

    @Operation(summary = "Get all employee.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllEmployee() {
        return employeeServiceImpl.getAllEmployee();
    }


    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateEmployeeWithPatch(
            @RequestBody Map<String, Object> updatedEmployee,
            @PathVariable("id") Integer existingId) {
        return employeeServiceImpl.updatePatch(existingId, updatedEmployee);
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateEmployeeWithPut(
            @RequestBody EmployeeDto updatedEmployee,
            @PathVariable("id") Integer existingId) {
        return employeeServiceImpl.updatePut(existingId, updatedEmployee);
    }

    @Operation(summary = "Delete the employee by an id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {
        return employeeServiceImpl.deleteById(existingId);
    }
}
