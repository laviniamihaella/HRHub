package ro.lavinia.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.mapper.EmployeeMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl {
    
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;


    public void save(EmployeeDto employeeDto, Integer departmentId, Integer jobPositionId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("The department id is not valid."));

        JobPosition jobPosition = jobPositionRepository.findById(jobPositionId)
                .orElseThrow(() -> new RuntimeException("The job position id is not valid."));

        Employee employee = EmployeeMapper.INSTANCE.EmployeeDtoToEmployeeEntity(employeeDto);

        employee.setDepartment(department);
        employee.setJobPosition(jobPosition);

        employeeRepository.save(employee);
    }


    public Optional<EmployeeDto> getAEmployeeById(Integer id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            return employeeOptional.map(EmployeeMapper.INSTANCE::EmployeeEntityToEmployeeDto);
        }else {
            throw new RuntimeException("The id for employee is not valid");
        }
    }
    public void deleteById(Integer id) {
        Optional<Employee> optionalProperty = employeeRepository.findById(id);
        if (optionalProperty.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for employee is not valid");
        }
    }

    public List<EmployeeDto> getAllEmployee() {
        try {
            return employeeRepository.findAll().stream()
                    .map(EmployeeMapper.INSTANCE::EmployeeEntityToEmployeeDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of attendance: " + e.getMessage());
        }
    }


    public void updatePatch(Integer existingId, Map<String, Object> Employee) {
        Employee existingEmployee = employeeRepository.findById(existingId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Employee.forEach((key, value) -> {
            switch (key) {
                case "FirstName":
                    if (value instanceof Date) {
                        existingEmployee.setFirstName(existingEmployee.getFirstName());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "LastName":
                    if (value instanceof Date) {
                        existingEmployee.setLastName(existingEmployee.getLastName());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "Address":
                    if (value instanceof Date) {
                        existingEmployee.setAddress(existingEmployee.getAddress());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "BirthDate":
                    if (value instanceof String) {
                        existingEmployee.setBirthDate(existingEmployee.getBirthDate());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                case "Salary":
                    if (value instanceof String) {
                        existingEmployee.setSalary(existingEmployee.getSalary());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        employeeRepository.save(existingEmployee);
    }

    public void updatePut(Integer existingId, EmployeeDto employeeDto) {
        var employeeOptional = employeeRepository.findById(existingId);
        if (employeeOptional.isEmpty()) {
            throw new RuntimeException("Employee with ID " + existingId + " not found");
        }
        Employee existingEmployee = employeeOptional.get();

        existingEmployee.setFirstName(employeeDto.getFirstName());
        existingEmployee.setLastName(employeeDto.getLastName());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setBirthDate(employeeDto.getBirthDate());
        existingEmployee.setSalary(employeeDto.getSalary());

        employeeRepository.save(existingEmployee);
    }
}
