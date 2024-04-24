package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.AttendanceDto;
import ro.lavinia.entity.Attendance;
import ro.lavinia.swagger.AttendanceSwagger;
import ro.lavinia.service.AttendanceServiceImpl;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
@PreAuthorize("hasRole('ADMIN')")
public class AttendanceController implements AttendanceSwagger {
    private final AttendanceServiceImpl attendanceServiceImpl;

    @Operation(summary = "Save a new attendance")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    @PostMapping("/{employeeId}")
    public ResponseEntity<?> createAttendance(@RequestBody AttendanceDto attendanceDto,
                                              @PathVariable Integer employeeId) {
        return attendanceServiceImpl.save(attendanceDto,employeeId);
    }

    @Operation(summary = "Get a attendance by Id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getById(@PathVariable("id") Integer existingId) {
        return attendanceServiceImpl.getAnAttendanceById(existingId);
    }

    @Operation(summary = "Get all attendance.")
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<?> getAllAttendance(){
        return attendanceServiceImpl.getAllAttendance();
    }

    @Operation(summary = "Get all attendance for an employee.")
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<?> getAllAttendanceForAnEmployee(@PathVariable Integer employeeId) {
        return attendanceServiceImpl.getAllAttendanceForAnEmployee(employeeId);
    }


    @Operation(summary = "Update attendance with patch.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateAttendanceWithPatch(
            @RequestBody Map<String, Object> updatedAttendance,
            @PathVariable("id") Integer existingId) {
        return attendanceServiceImpl.updatePatch(existingId, updatedAttendance);
    }

    @Operation(summary = "Update attendance with put.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<?> updateAttendanceWithPut(
            @RequestBody AttendanceDto updatedAttendance ,
            @PathVariable("id") Integer existingId) {
       return attendanceServiceImpl.updatePut(existingId, updatedAttendance);
    }

    @Operation(summary = "Delete the attendance by an id.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteById(@PathVariable("id")  Integer existingId) {
        return attendanceServiceImpl.deleteById(existingId);
    }

}
