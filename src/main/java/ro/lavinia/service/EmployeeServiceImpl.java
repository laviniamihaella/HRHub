package ro.lavinia.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.mapper.EmployeeMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.time.LocalDate;
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
                .orElseThrow(() -> new EntityNotFoundException("The department id is not valid."));

        JobPosition jobPosition = jobPositionRepository.findById(jobPositionId)
                .orElseThrow(() -> new EntityNotFoundException("The job position id is not valid."));

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


    public void updatePatch(Integer existingId, Map<String, Object> updatedEmployee) {

        var employeeOptional = employeeRepository.findById(existingId);
        if (employeeOptional.isEmpty()) {
            throw new EntityNotFoundException("Employee NOT Found");
        }
        Employee employee = employeeOptional.get();
        for (Map.Entry<String, Object> entry : updatedEmployee.entrySet()) {
            switch (entry.getKey()) {
                case "FirstName","firstName":
                        employee.setFirstName((String) entry.getValue());
                    break;
                case "LastName","lastName":
                        employee.setLastName((String) entry.getValue());
                    break;
                case "Address","address":
                        employee.setAddress((String) entry.getValue());
                    break;
                case "BirthDate","birthDate":
                        employee.setBirthDate((LocalDate) entry.getValue());
                    break;
                case "Salary","salary":
                        employee.setSalary((Float) entry.getValue());
                    break;
                default:
                    throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
            }
        }

        employeeRepository.save(employee);
    }

    public void updatePut(Integer existingId, Employee updatedEmployee) {
        var employeeOptional = employeeRepository.findById(existingId);
        if (employeeOptional.isEmpty()) {
            throw new EntityNotFoundException("Employee with ID " + existingId + " not found");
        }
        Employee existingEmployee = employeeOptional.get();

        existingEmployee.setFirstName(updatedEmployee.getFirstName());
        existingEmployee.setLastName(updatedEmployee.getLastName());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setBirthDate(updatedEmployee.getBirthDate());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        employeeRepository.save(existingEmployee);
    }
}
