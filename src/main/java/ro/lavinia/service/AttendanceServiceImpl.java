package ro.lavinia.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.AttendanceDto;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.exception.DepartmentNotFoundException;
import ro.lavinia.exception.EmployeeNotFoundException;
import ro.lavinia.mapper.AttendanceMapper;
import ro.lavinia.mapper.LeaveRequestMapper;
import ro.lavinia.repository.AttendanceRepository;
import ro.lavinia.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl {
    private  final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public Attendance save(AttendanceDto attendanceDto, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        Department department = employee.getDepartment(); // Obținem departamentul asociat angajatului

        if (department == null) {
            throw new DepartmentNotFoundException("Department not found for employee with id: " + employeeId);
        }

        Attendance attendance = AttendanceMapper.INSTANCE.AttendanceDtoToAttendanceEntity(attendanceDto);
        attendance.setEmployee(employee);
        attendance.setDepartment(department); // Setăm departamentul în cererea de concediu

        return  attendanceRepository.save(attendance);
    }


    public Optional<AttendanceDto> getAnAttendanceById(Integer id) {
        try {
            Optional<Attendance> attendanceOptional = attendanceRepository.findById(id);
            return attendanceOptional.map(AttendanceMapper.INSTANCE::AttendanceEntityToAttendanceDto);
        }catch (Exception e) {
            throw new RuntimeException("Failed to get an attendance by id: " + e.getMessage());
        }

    }
    public void deleteById(Integer id) {
        Optional<Attendance> optionalProperty = attendanceRepository.findById(id);
        if (optionalProperty.isPresent()) {
            attendanceRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for attendance is not valid");
        }
    }

    public List<AttendanceDto> getAllAttendance() {
        try {
            return attendanceRepository.findAll().stream()
                    .map(AttendanceMapper.INSTANCE::AttendanceEntityToAttendanceDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of attendance: " + e.getMessage());
        }
    }

    public void updatePatch(Integer existingId, Map<String, Object> attendance) {
        Attendance existingAttendance = attendanceRepository.findById(existingId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));

        attendance.forEach((key, value) -> {
            switch (key) {
                case "Data":
                    if (value instanceof Date) {
                        existingAttendance.setData((LocalDate) value);
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Data'");
                    }
                    break;
                case "ArrivalTime":
                    if (value instanceof String) {
                        existingAttendance.setArrivalTime(LocalTime.parse((String) value));
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "DepartureTime":
                    if (value instanceof Integer) {
                        existingAttendance.setDepartureTime(LocalTime.ofSecondOfDay((Integer) value));
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'DepartureTime'");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        attendanceRepository.save(existingAttendance);
    }

    public void updatePut(Integer existingId, AttendanceDto attendanceDto) {
        var attendanceOptional = attendanceRepository.findById(existingId);
        if (attendanceOptional.isEmpty()) {
            throw new RuntimeException("Attendance with ID " + existingId + " not found");
        }
        Attendance existingAttendance = attendanceOptional.get();

        existingAttendance.setData(attendanceDto.getData());
        existingAttendance.setArrivalTime(attendanceDto.getArrivalTime());
        existingAttendance.setDepartureTime(attendanceDto.getDepartureTime());

        attendanceRepository.save(existingAttendance);
    }

}
