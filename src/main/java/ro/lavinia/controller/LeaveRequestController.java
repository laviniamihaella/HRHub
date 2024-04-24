package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.swagger.LeaveRequestSwagger;
import ro.lavinia.service.LeaveRequestServiceImpl;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave-Request")
@PreAuthorize("hasRole('ADMIN')")
public class LeaveRequestController implements LeaveRequestSwagger {
    private final LeaveRequestServiceImpl leaveRequestServiceImpl;

    @Operation(summary = "Save a new leave request")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<?> createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto,
                                   @RequestParam("employeeId") Integer employeeId) {
        return leaveRequestServiceImpl.save(leaveRequestDto, employeeId);
    }

    @Operation(summary = "Get all leave request.")
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getAllLeaveRequest() {
        return leaveRequestServiceImpl.getAllLeaveRequest();
    }

    @Operation(summary = "Get a leave request by Id.")
    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return leaveRequestServiceImpl.getALeaveRequestById(id);
    }
    @Operation(summary = "Get all leave request for an employee.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllLeaveRequestForAnEmployee(@PathVariable Integer employeeId) {
        return leaveRequestServiceImpl.getAllLeaveRequestForAnEmployee(employeeId);
    }

    @Operation(summary = "Update leave request with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateLeaveRequestWithPatch(
            @RequestBody Map<String, Object> updatedLeaveRequest,
            @PathVariable("id") Integer existingId) {
        return leaveRequestServiceImpl.updatePatch(existingId, updatedLeaveRequest);
    }

    @Operation(summary = "Update leave request with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateLeaveRequestWithPut(
            @RequestBody LeaveRequestDto updatedLeaveRequest ,
            @PathVariable("id") Integer existingId) {
        return leaveRequestServiceImpl.updatePut(existingId, updatedLeaveRequest);
    }

    @Operation(summary = "Delete the leave request by an id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return leaveRequestServiceImpl.deleteById(id);
    }
    
}
