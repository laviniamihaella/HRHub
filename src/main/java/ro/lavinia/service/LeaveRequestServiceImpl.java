package ro.lavinia.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.exception.DepartmentNotFoundException;
import ro.lavinia.exception.EmployeeNotFoundException;
import ro.lavinia.mapper.LeaveRequestMapper;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.LeaveRequestRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl {
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequest save(LeaveRequestDto leaveRequestDto, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        Department department = employee.getDepartment(); // Obținem departamentul asociat angajatului

        if (department == null) {
            throw new DepartmentNotFoundException("Department not found for employee with id: " + employeeId);
        }

        LeaveRequest leaveRequest = LeaveRequestMapper.INSTANCE.LeaveRequestDtoToLeaveRequestEntity(leaveRequestDto);
        leaveRequest.setEmployee(employee);
        leaveRequest.setDepartment(department); // Setăm departamentul în cererea de concediu

        return leaveRequestRepository.save(leaveRequest);
    }

    public Optional<LeaveRequestDto> getALeaveRequestById(Integer id) {
        try {
            Optional<LeaveRequest> leaveRequestOptional= leaveRequestRepository.findById(id);
            return leaveRequestOptional.map(LeaveRequestMapper.INSTANCE::LeaveRequestEntityToLeaveRequestDto);
        }catch (Exception e) {
            throw new RuntimeException("Failed to get an leave request by id: " + e.getMessage());
        }
    }
    public List<LeaveRequestDto> getAllLeaveRequest() {
        try {
            return leaveRequestRepository.findAll().stream()
                    .map(LeaveRequestMapper.INSTANCE::LeaveRequestEntityToLeaveRequestDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of leave request: " + e.getMessage());
        }
    }
    public void deleteById(Integer id) {
        Optional<LeaveRequest> optionalProperty = leaveRequestRepository.findById(id);
        if (optionalProperty.isPresent()) {
            leaveRequestRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for job position is not valid");
        }
    }



    public void updatePatch(Integer existingId, Map<String, Object> leaveRequest) {
        LeaveRequest existingleaveRequest = leaveRequestRepository.findById(existingId)
                .orElseThrow(() -> new EntityNotFoundException("leaveRequest not found"));

        leaveRequest.forEach((key, value) -> {
            switch (key) {
                case "Reason":
                    if (value instanceof Date) {
                        existingleaveRequest.setReason(existingleaveRequest.getReason());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "Status":
                    if (value instanceof String) {
                        existingleaveRequest.setStatus(existingleaveRequest.getStatus());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "StartDate":
                    if (value instanceof String) {
                        existingleaveRequest.setStartDate(existingleaveRequest.getStartDate());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "EndDate":
                    if (value instanceof String) {
                        existingleaveRequest.setEndDate(existingleaveRequest.getEndDate());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        leaveRequestRepository.save(existingleaveRequest);
    }

    public void updatePut(Integer existingId, LeaveRequestDto leaveRequestDto) {
        var leaveRequestOptional = leaveRequestRepository.findById(existingId);
        if (leaveRequestOptional.isEmpty()) {
            throw new RuntimeException("leaveRequest NOT Found");
        }
        LeaveRequest existingleaveRequest = leaveRequestOptional.get();
        existingleaveRequest.setStatus(leaveRequestDto.getStatus());
        existingleaveRequest.setReason(leaveRequestDto.getReason());
        existingleaveRequest.setStartDate(leaveRequestDto.getStartDate());
        existingleaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequestRepository.save(existingleaveRequest);
    }
}
