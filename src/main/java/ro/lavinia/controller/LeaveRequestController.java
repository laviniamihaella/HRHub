package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.interfacesForSwagger.LeaveRequestForSwagger;
import ro.lavinia.service.LeaveRequestServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaveRequest")
public class LeaveRequestController implements LeaveRequestForSwagger {
    private final LeaveRequestServiceImpl leaveRequestServiceImpl;

    @Operation(summary = "Save a new leave request")
    @PostMapping("/create")
    public void createLeaveRequest(@RequestBody LeaveRequestDto leaveRequestDto,
                                   @RequestParam("employeeId") Integer employeeId) {
        leaveRequestServiceImpl.save(leaveRequestDto, employeeId);
    }

    @Operation(summary = "Get a leave request by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<LeaveRequestDto> getById(@PathVariable Integer id) {
        return leaveRequestServiceImpl.getALeaveRequestById(id);
    }

    @Operation(summary = "Get all leave request.")
    @GetMapping("/all-leaveRequest")
    public List<LeaveRequestDto> getAllLeaveRequest() {
        return leaveRequestServiceImpl.getAllLeaveRequest();
    }


    @Operation(summary = "Update leave request with patch.")
    @PatchMapping("/update-leaveRequest-patch/{id}")
    public void updateLeaveRequestWithPatch(
            @RequestBody Map<String, Object> leaveRequest,
            @PathVariable("id") Integer existingId) {
        leaveRequestServiceImpl.updatePatch(existingId, leaveRequest);
    }

    @Operation(summary = "Update leave request with put.")
    @PutMapping("/update-leaveRequest-put/{id}")
    public void updateLeaveRequestWithPut(
            @RequestBody LeaveRequestDto leaveRequestDto ,
            @PathVariable("id") Integer existingId) {
        leaveRequestServiceImpl.updatePut(existingId, leaveRequestDto);
    }

    @Operation(summary = "Delete the leave request by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
        leaveRequestServiceImpl.deleteById(id);
    }
    
}
