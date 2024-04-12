package ro.lavinia.controller;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.lavinia.dto.AttendanceDto;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.interfacesForSwagger.AttendanceForSwagger;
import ro.lavinia.service.AttendanceServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController implements AttendanceForSwagger {
    private final AttendanceServiceImpl attendanceServiceImpl;

    @Operation(summary = "Save a new attendance")
    @PostMapping("/create")
    public void createAttendance(@RequestBody AttendanceDto attendanceDto,
                                   @RequestParam("employeeId") Integer employeeId) {
        attendanceServiceImpl.save(attendanceDto,employeeId);
    }

    @Operation(summary = "Get a attendance by Id.")
    @GetMapping("/get-by-id/{id}")
    public Optional<AttendanceDto> getById(@PathVariable Integer id) {
        return attendanceServiceImpl.getAnAttendanceById(id);
    }

    @Operation(summary = "Get all attendance.")
    @GetMapping("/all-attendance")
    public List<AttendanceDto> getAllAttendance() {
        return attendanceServiceImpl.getAllAttendance();
    }


    @Operation(summary = "Update attendance with patch.")
    @PatchMapping("/update-attendance-patch/{id}")
    public void updateAttendanceWithPatch(
            @RequestBody Map<String, Object> property,
            @PathVariable("id") Integer existingId) {
        attendanceServiceImpl.updatePatch(existingId, property);
    }

    @Operation(summary = "Update attendance with put.")
    @PutMapping("/update-attendance-put/{id}")
    public void updateAttendanceWithPut(
            @RequestBody AttendanceDto attendanceDto ,
            @PathVariable("id") Integer existingId) {
        attendanceServiceImpl.updatePut(existingId, attendanceDto);
    }

    @Operation(summary = "Delete the attendance by an id.")
    @DeleteMapping("/delete-by-id/{id}")
    public void deleteById(@PathVariable Integer id) {
        attendanceServiceImpl.deleteById(id);
    }


}
