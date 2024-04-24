package ro.lavinia.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.service.JobPositionServiceImpl;
import ro.lavinia.swagger.JobPositionSwagger;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-Position")
@PreAuthorize("hasRole('ADMIN')")
public class JobPositionController implements JobPositionSwagger {

    private final JobPositionServiceImpl jobPositionServiceImpl;

    @Operation(summary = "Save a new job position")
    @PostMapping("/{departmentId}")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> createJobPosition(@RequestBody JobPositionDto jobPositionDto, @PathVariable Integer departmentId) {
        return jobPositionServiceImpl.save(jobPositionDto, departmentId);
    }

    @Operation(summary = "Get a job position by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return jobPositionServiceImpl.getAJobPositionById(existingId);
    }

    @Operation(summary = "Get a list of job position.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllJobPositions() {
        return jobPositionServiceImpl.getAllJobPositions();
    }

    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateJobPositionWithPatch(
            @RequestBody Map<String, Object> updatedJobPosition,
            @PathVariable("id") Integer existingId) {
        ResponseEntity<?> responseEntity = jobPositionServiceImpl.updatePatch(existingId, updatedJobPosition);
        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body((String) responseEntity.getBody());
    }

    @Operation(summary = "Update job position with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<String> updateJobPositionWithPut(
            @RequestBody JobPositionDto updatedJobPosition,
            @PathVariable("id") Integer existingId) {

        ResponseEntity<?> responseEntity = jobPositionServiceImpl.updatePut(existingId, updatedJobPosition);
        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body((String) responseEntity.getBody());
    }

    @Operation(summary = "Delete the job position by an id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {

        return jobPositionServiceImpl.deleteById(existingId);
    }

}