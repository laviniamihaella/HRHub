package ro.lavinia.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.LeaveRequestDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.LeaveRequest;
import ro.lavinia.entity.Project;
import ro.lavinia.exception.DepartmentNotFoundException;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.mapper.LeaveRequestMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.LeaveRequestRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl {
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;
    private  final DepartmentRepository departmentRepository;

    public LeaveRequest save(LeaveRequestDto leaveRequestDto, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));

        Department department = employee.getDepartment();

        if (department == null) {
            throw new EntityNotFoundException("Department not found for employee with id: " + employeeId);
        }

        LeaveRequest leaveRequest = LeaveRequestMapper.INSTANCE.LeaveRequestDtoToLeaveRequestEntity(leaveRequestDto);
        leaveRequest.setEmployee(employee);
        leaveRequest.setDepartment(department);

        return leaveRequestRepository.save(leaveRequest);
    }

    public Optional<LeaveRequestDto> getALeaveRequestById(Integer id) {
        try {
            Optional<LeaveRequest> leaveRequestOptional= leaveRequestRepository.findById(id);
            return leaveRequestOptional.map(LeaveRequestMapper.INSTANCE::LeaveRequestEntityToLeaveRequestDto);
        }catch (Exception e) {
            throw new EntityNotFoundException("Failed to get an leave request by id: " + e.getMessage());
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
            throw new RuntimeException("The id for leave request is not valid");
        }
    }

    public void updatePatch(Integer existingId, Map<String, Object> updatedLeaveRequest) {


        var leaveRequestOptional = leaveRequestRepository.findById(existingId);
        if (leaveRequestOptional.isEmpty()) {
            throw new EntityNotFoundException("LeaveRequest NOT Found");
        }
        LeaveRequest leaveRequest = leaveRequestOptional.get();
        for (Map.Entry<String, Object> entry : updatedLeaveRequest.entrySet()) {
            switch (entry.getKey()) {
                case "Reason":
                    leaveRequest.setReason((String) entry.getValue());
                    break;
                case "Status":
                    leaveRequest.setStatus((String) entry.getValue());
                    break;
                case "StartDate":
                    leaveRequest.setStartDate((LocalDate) entry.getValue());
                    break;
                case "EndDate":
                    leaveRequest.setEndDate((LocalDate) entry.getValue());
                    break;
                default:
                    throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
            }
        }
        leaveRequestRepository.save(leaveRequest);
    }

    public void updatePut(Integer existingId, LeaveRequest updatedLeaveRequest) {
        var leaveRequestOptional = leaveRequestRepository.findById(existingId);
        if (leaveRequestOptional.isEmpty()) {
            throw new EntityNotFoundException("LeaveRequest NOT Found");
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
                        .orElseThrow(() -> new EntityNotFoundException("Employee NOT Found with ID: " + departmentId));
                existingLeaveRequest.setDepartment(department);
            }
        }
        existingLeaveRequest.setStatus(updatedLeaveRequest.getStatus());
        existingLeaveRequest.setReason(updatedLeaveRequest.getReason());
        existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
        existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());


        leaveRequestRepository.save(existingLeaveRequest);
    }

}
