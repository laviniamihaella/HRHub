package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.AttendanceDto;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Employee;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.exception.IncompleteFieldsException;
import ro.lavinia.exception.InvalidPeriodException;
import ro.lavinia.mapper.AttendanceMapper;
import ro.lavinia.repository.AttendanceRepository;
import ro.lavinia.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public ResponseEntity<?> save(AttendanceDto attendanceDto, Integer employeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + " not found."));

            if (employee.getDepartment() == null) {
                throw new EntityNotFoundException("Department not found for employee with ID " + employeeId + ".");
            }

            if (attendanceDto.getArrivalTime() == null ||
                    attendanceDto.getDepartureTime() == null ||
                    attendanceDto.getData() == null) {
                throw new IncompleteFieldsException("Attendance information is incomplete.");
            }

            Attendance attendance = AttendanceMapper.INSTANCE.AttendanceDtoToAttendanceEntity(attendanceDto);
            attendance.setEmployee(employee);
            attendance.setDepartment(employee.getDepartment());

            attendanceRepository.save(attendance);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + employeeId + " not found.", HttpStatus.NOT_FOUND);
        } catch (IncompleteFieldsException e) {
            return new ResponseEntity<>("Attendance information is incomplete.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Attendance has been successfully saved.", HttpStatus.OK);
    }


    public ResponseEntity<?> getAnAttendanceById(Integer existingId) {
        try {
            Optional<Attendance> attendanceOptional = attendanceRepository.findById(existingId);
            if (attendanceOptional.isPresent()) {
                Attendance attendance = attendanceOptional.get();
                AttendanceDto attendanceDto = AttendanceMapper.INSTANCE.AttendanceEntityToAttendanceDto(attendance);
                return new ResponseEntity<>(attendanceDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Attendance with ID " + existingId + " NOT Found.");
            }

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Attendance with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllAttendance() {
        try {
            List<Attendance> attendanceList = attendanceRepository.findAll();
            if (attendanceList.isEmpty()) {
                throw new EntityNotFoundException("Attendance list  NOT Found.");
            } else {
                List<AttendanceDto> attendanceDtoList = attendanceList.stream().toList()
                        .stream().map(AttendanceMapper.INSTANCE::AttendanceEntityToAttendanceDto)
                        .toList();
                return new ResponseEntity<>(attendanceDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Attendance list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> getAllAttendanceForAnEmployee(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityNotFoundException("Employee with ID " + employeeId + " not found."));
        }

        List<Attendance> attendanceList = attendanceRepository.findByEmployee(employeeOptional.get());
        if (attendanceList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityNotFoundException("Leave request list not found for employee with ID " + employeeId));
        } else {
            List<AttendanceDto> attendanceDtoList = attendanceList.stream()
                    .map(AttendanceMapper.INSTANCE::AttendanceEntityToAttendanceDto)
                    .toList();
            return ResponseEntity.ok(attendanceDtoList);
        }
    }


    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedAttendance) {
        try {

            var attendanceOptional = attendanceRepository.findById(existingId);
            if (attendanceOptional.isEmpty()) {
                throw new EntityNotFoundException("Attendance with ID " + existingId + " not found");
            }
            Attendance attendance = attendanceOptional.get();

            for (Map.Entry<String, Object> entry : updatedAttendance.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "data":
                        if (entry.getValue() instanceof String dateString) {
                            LocalDate date = LocalDate.parse(dateString);
                            attendance.setData(date);
                        } else {
                            return new ResponseEntity<>("Invalid data format for 'data' field", HttpStatus.BAD_REQUEST);
                        }
                        break;

                    case "arrivaltime":
                        if (entry.getValue() instanceof String arrivalTimeString) {
                            LocalTime arrivalTime = LocalTime.parse(arrivalTimeString);
                            attendance.setArrivalTime(arrivalTime);
                        } else {
                            return new ResponseEntity<>("Invalid field: " + key, HttpStatus.BAD_REQUEST);
                        }
                        break;
                    case "departuretime":
                        if (entry.getValue() instanceof String departureTimeString) {
                            LocalTime departureTime = LocalTime.parse(departureTimeString);
                            attendance.setDepartureTime(departureTime);
                        } else {
                            return new ResponseEntity<>("Invalid field: " + key, HttpStatus.BAD_REQUEST);
                        }
                        break;
                    default:
                        throw new FieldNotFoundException("Unknown field: " + key);
                }
            }
            if (attendance.getArrivalTime() != null && attendance.getDepartureTime() != null &&
                    attendance.getArrivalTime().isAfter(attendance.getDepartureTime())) {
                throw new InvalidPeriodException("Arrival time can not be after departure time");
            }
            attendanceRepository.save(attendance);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Attendance with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Attendance with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }

    public ResponseEntity<?> updatePut(Integer existingId, AttendanceDto updatedAttendance) {
        try {
            if (updatedAttendance.getArrivalTime() != null && updatedAttendance.getDepartureTime() != null &&
                    updatedAttendance.getArrivalTime().isAfter(updatedAttendance.getDepartureTime())) {
                throw new InvalidPeriodException("Arrival Time can not be after Departure Time");
            }
            var attendanceOptional = attendanceRepository.findById(existingId);
            if (attendanceOptional.isEmpty()) {
                throw new EntityNotFoundException("Attendance with ID " + existingId + " not found");
            }
            Attendance existingAttendance = attendanceOptional.get();

            existingAttendance.setData(updatedAttendance.getData());
            existingAttendance.setArrivalTime(updatedAttendance.getArrivalTime());
            existingAttendance.setDepartureTime(updatedAttendance.getDepartureTime());

            if (existingAttendance.getArrivalTime() == null ||
                    existingAttendance.getDepartureTime() == null ||
                    existingAttendance.getData() == null) {
                return new ResponseEntity<>("Attendance information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            attendanceRepository.save(existingAttendance);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Attendance with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Attendance with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<Attendance> optionalAttendance = attendanceRepository.findById(existingId);
            if (optionalAttendance.isPresent()) {
                attendanceRepository.deleteById(existingId);
                return new ResponseEntity<>("Attendance with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Attendance with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Attendance with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


}
