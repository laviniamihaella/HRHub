package ro.lavinia.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.JobPositionDto;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.service.JobPositionServiceImpl;
import ro.lavinia.swagger.JobPositionSwagger;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/job-Position")
public class JobPositionController implements JobPositionSwagger {

    private final JobPositionServiceImpl jobPositionServiceImpl;

    @Operation(summary = "Save a new job position")
    @PostMapping("/{departmentId}")
    public ResponseEntity<?> createJobPosition(@RequestBody JobPositionDto jobPositionDto, @PathVariable Integer departmentId) {
        return jobPositionServiceImpl.save(jobPositionDto, departmentId);
    }

    @Operation(summary = "Get a job position by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return jobPositionServiceImpl.getAJobPositionById(existingId);
    }

    @Operation(summary = "Get a list of job position.")
    @GetMapping
    public ResponseEntity<?> getAllJobPositions() {
        return jobPositionServiceImpl.getAllJobPositions();
    }

    @Operation(summary = "Update job position with patch.")
    @PatchMapping("/{id}")
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
    public ResponseEntity<String> updateJobPositionWithPut(
            @RequestBody JobPosition updatedJobPosition,
            @PathVariable("id") Integer existingId) {

        ResponseEntity<?> responseEntity = jobPositionServiceImpl.updatePut(existingId, updatedJobPosition);
        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body((String) responseEntity.getBody());
    }

    @Operation(summary = "Delete the job position by an id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer existingId) {

        return jobPositionServiceImpl.deleteById(existingId);
    }

}