package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.swagger.LeaveRequestSwagger;
import ro.lavinia.service.LeaveRequestServiceImpl;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leave-Request")
public class LeaveRequestController implements LeaveRequestSwagger {
    private final LeaveRequestServiceImpl leaveRequestServiceImpl;

    @Operation(summary = "Save a new leave request")
    @PostMapping
    public ResponseEntity<?> createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto,
                                   @RequestParam("employeeId") Integer employeeId) {
        return leaveRequestServiceImpl.save(leaveRequestDto, employeeId);
    }

    @Operation(summary = "Get a leave request by Id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return leaveRequestServiceImpl.getALeaveRequestById(id);
    }

    @Operation(summary = "Get all leave request.")
    @GetMapping
    public ResponseEntity<?> getAllLeaveRequest() {
        return leaveRequestServiceImpl.getAllLeaveRequest();
    }


    @Operation(summary = "Update leave request with patch.")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLeaveRequestWithPatch(
            @RequestBody Map<String, Object> updatedLeaveRequest,
            @PathVariable("id") Integer existingId) {
        return leaveRequestServiceImpl.updatePatch(existingId, updatedLeaveRequest);
    }

    @Operation(summary = "Update leave request with put.")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLeaveRequestWithPut(
            @RequestBody LeaveRequest updatedLeaveRequest ,
            @PathVariable("id") Integer existingId) {
        return leaveRequestServiceImpl.updatePut(existingId, updatedLeaveRequest);
    }

    @Operation(summary = "Delete the leave request by an id.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        return leaveRequestServiceImpl.deleteById(id);
    }
    
}
