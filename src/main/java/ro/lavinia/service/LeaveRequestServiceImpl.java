package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.entity.User;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.exception.InvalidPeriodException;
import ro.lavinia.mapper.LeaveRequestMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.LeaveRequestRepository;
import ro.lavinia.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl {
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> save(LeaveRequestDto leaveRequestDto, Integer employeeId) {
        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + employeeId + "  NOT Found."));

            Department department = employee.getDepartment();

            if (department == null) {
                throw new EntityNotFoundException("Department for employee with id: " + employeeId + "  NOT Found.");
            }
            if (leaveRequestDto.getStartDate() == null || leaveRequestDto.getEndDate() == null) {
                return new ResponseEntity<>("Leave Request information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            LeaveRequest leaveRequest = LeaveRequestMapper.INSTANCE.LeaveRequestDtoToLeaveRequestEntity(leaveRequestDto);
            leaveRequest.setEmployee(employee);
            leaveRequest.setDepartment(department);

            leaveRequestRepository.save(leaveRequest);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + employeeId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Leave Request has been successfully saved.", HttpStatus.OK);
    }

    public ResponseEntity<?> getALeaveRequestById(Integer existingId) {
        try {
            Optional<LeaveRequest> leaveRequestOptional = leaveRequestRepository.findById(existingId);
            if (leaveRequestOptional.isPresent()) {
                LeaveRequest leaveRequest = leaveRequestOptional.get();
                LeaveRequestDto leaveRequestDto = LeaveRequestMapper.INSTANCE.LeaveRequestEntityToLeaveRequestDto(leaveRequest);
                return new ResponseEntity<>(leaveRequestDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Leave Request with ID " + existingId + " NOT Found.");
            }

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Leave Request with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllLeaveRequestForAnEmployee(Integer employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityNotFoundException("Employee with ID " + employeeId + " not found."));
        }

        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findByEmployee(employeeOptional.get());
        if (leaveRequestList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityNotFoundException("Leave request list not found for employee with ID " + employeeId));
        } else {
            List<LeaveRequestDto> leaveRequestDtoList = leaveRequestList.stream()
                    .map(LeaveRequestMapper.INSTANCE::LeaveRequestEntityToLeaveRequestDto)
                    .toList();
            return ResponseEntity.ok(leaveRequestDtoList);
        }
    }


    public ResponseEntity<?> getAllLeaveRequest() {
        try {
            List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
            if (leaveRequestList.isEmpty()) {
                throw new EntityNotFoundException("Leave Request list  NOT Found.");
            } else {
                List<LeaveRequestDto> leaveRequestDtoList = leaveRequestList
                        .stream().toList().stream().map(LeaveRequestMapper.INSTANCE::LeaveRequestEntityToLeaveRequestDto)
                        .toList();
                return new ResponseEntity<>(leaveRequestDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Leave Request list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedLeaveRequest) {
        try {

            var leaveRequestOptional = leaveRequestRepository.findById(existingId);
            if (leaveRequestOptional.isEmpty()) {
                throw new EntityNotFoundException("LeaveRequest NOT Found");
            }
            LeaveRequest leaveRequest = leaveRequestOptional.get();
            for (Map.Entry<String, Object> entry : updatedLeaveRequest.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "reason":
                        leaveRequest.setReason((String) entry.getValue());
                        break;
                    case "status":
                        leaveRequest.setStatus((String) entry.getValue());
                        break;
                    case "startdate":
                        if (entry.getValue() instanceof String startDateString) {
                            LocalDate startDate = LocalDate.parse(startDateString);
                            leaveRequest.setStartDate(startDate);
                        } else {
                            throw new InvalidPeriodException("Invalid data format for 'start date' field");
                        }
                        break;
                    case "enddate":
                        if (entry.getValue() instanceof String endDateString) {
                            LocalDate endDate = LocalDate.parse(endDateString);
                            leaveRequest.setEndDate(endDate);
                        } else {
                            throw new InvalidPeriodException("Invalid data format for 'end date' field");
                        }
                        break;
                    default:
                        throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
                }
            }
            if (leaveRequest.getStartDate() != null && leaveRequest.getEndDate() != null &&
                    leaveRequest.getStartDate().isAfter(leaveRequest.getEndDate())) {
                throw new InvalidPeriodException("Start date cannot be after end date");
            }

            leaveRequestRepository.save(leaveRequest);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Leave Request with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Leave Request with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }

    public ResponseEntity<?> updatePut(Integer existingId, LeaveRequestDto updatedLeaveRequest) {
        try {


            var leaveRequestOptional = leaveRequestRepository.findById(existingId);
            if (leaveRequestOptional.isEmpty()) {
                throw new EntityNotFoundException("Leave Request NOT Found");
            }
            LeaveRequest existingLeaveRequest = leaveRequestOptional.get();

            Employee existingEmployee = updatedLeaveRequest.getEmployee();
            if (existingEmployee != null) {
                Integer employeeId = existingEmployee.getId();
                if (employeeId != null) {
                    Employee employee = employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new EntityNotFoundException("Employee NOT Found with ID: " + employeeId));
                    existingLeaveRequest.setEmployee(employee);
                }
            }

            Department existingDepartment = updatedLeaveRequest.getDepartment();
            if (existingDepartment != null) {
                Integer departmentId = existingDepartment.getId();
                if (departmentId != null) {
                    Department department = departmentRepository.findById(departmentId)
                            .orElseThrow(() -> new EntityNotFoundException("Department NOT Found with ID: " + departmentId));
                    existingLeaveRequest.setDepartment(department);
                }
            }
            existingLeaveRequest.setStatus(updatedLeaveRequest.getStatus());
            existingLeaveRequest.setReason(updatedLeaveRequest.getReason());
            existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
            existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());

            if (existingLeaveRequest.getStartDate() == null || existingLeaveRequest.getEndDate() == null) {
                return new ResponseEntity<>("Leave Request information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            leaveRequestRepository.save(existingLeaveRequest);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Leave Request with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Leave Request with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(existingId);
            if (optionalLeaveRequest.isPresent()) {
                leaveRequestRepository.deleteById(existingId);
                return new ResponseEntity<>("Leave Request with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Leave Request with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Leave Request with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

}
