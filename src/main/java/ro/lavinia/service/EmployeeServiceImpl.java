package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.exception.IncompleteFieldsException;
import ro.lavinia.mapper.EmployeeMapper;
import ro.lavinia.repository.DepartmentRepository;
import ro.lavinia.repository.EmployeeRepository;
import ro.lavinia.repository.JobPositionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;

    public ResponseEntity<?> save(EmployeeDto employeeDto, Integer departmentId, Integer jobPositionId) {
        try {

            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new EntityNotFoundException("The department id is not valid."));

            JobPosition jobPosition = jobPositionRepository.findById(jobPositionId)
                    .orElseThrow(() -> new EntityNotFoundException("The job position id is not valid."));

            if (employeeDto.getSalary() == null ||
                    employeeDto.getBirthDate() == null ||
                    employeeDto.getAddress() == null || employeeDto.getAddress().isEmpty() ||
                    employeeDto.getFirstName() == null || employeeDto.getFirstName().isEmpty() ||
                    employeeDto.getLastName() == null || employeeDto.getLastName().isEmpty()) {
               throw new IncompleteFieldsException("Employee information is incomplete.");
            }

            Employee employee = EmployeeMapper.INSTANCE.EmployeeDtoToEmployeeEntity(employeeDto);

            employee.setDepartment(department);
            employee.setJobPosition(jobPosition);

            employeeRepository.save(employee);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("The ID for Department or Job Position NOT Found.", HttpStatus.NOT_FOUND);
        }catch (IncompleteFieldsException e) {
            return new ResponseEntity<>("Employee information is incomplete.", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("An employee with this address already exist.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Employee has been successfully saved.", HttpStatus.OK);
    }


    public ResponseEntity<?> getAEmployeeById(Integer existingId) {
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(existingId);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                EmployeeDto employeeDto = EmployeeMapper.INSTANCE.EmployeeEntityToEmployeeDto(employee);
                return new ResponseEntity<>(employeeDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Employee with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> getAllEmployee() {
        try {
            List<Employee> employeeList = employeeRepository.findAll();
            if (employeeList.isEmpty()) {
                throw new EntityNotFoundException("Employee list  NOT Found.");
            } else {
                List<EmployeeDto> employeeDtoList = employeeList.stream().toList()
                        .stream().map(EmployeeMapper.INSTANCE::EmployeeEntityToEmployeeDto)
                        .toList();
                return new ResponseEntity<>(employeeDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedEmployee) {

        try {


            var employeeOptional = employeeRepository.findById(existingId);
            if (employeeOptional.isEmpty()) {
                throw new EntityNotFoundException("Employee with ID " + existingId + " not found");
            }
            Employee employee = employeeOptional.get();
            for (Map.Entry<String, Object> entry : updatedEmployee.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "firstname":
                        employee.setFirstName((String) entry.getValue());
                        break;
                    case "lastname":
                        employee.setLastName((String) entry.getValue());
                        break;
                    case "address":
                        employee.setAddress((String) entry.getValue());
                        break;
                    case "birthdate":
                        if (entry.getValue() instanceof String birthDateString) {
                            LocalDate birthDate = LocalDate.parse(birthDateString);
                            employee.setBirthDate(birthDate);
                        } else {
                            return new ResponseEntity<>("Invalid field: " + key, HttpStatus.BAD_REQUEST);
                        }
                        break;
                    case "salary":
                        employee.setSalary((Integer) entry.getValue());
                        break;
                    default:
                        throw new FieldNotFoundException("Field " + entry.getKey() + " not recognized");
                }
            }

            employeeRepository.save(employee);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Employee with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }


    public ResponseEntity<?> updatePut(Integer existingId, EmployeeDto updatedEmployee) {
        try {
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

            if (existingEmployee.getSalary() == null ||
                    existingEmployee.getBirthDate() == null ||
                    existingEmployee.getAddress() == null || existingEmployee.getAddress().isEmpty() ||
                    existingEmployee.getFirstName() == null || existingEmployee.getFirstName().isEmpty() ||
                    existingEmployee.getLastName() == null || existingEmployee.getLastName().isEmpty()) {
                return new ResponseEntity<>("Employee information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            employeeRepository.save(existingEmployee);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Employee with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(existingId);
            if (optionalEmployee.isPresent()) {
                employeeRepository.deleteById(existingId);
                return new ResponseEntity<>("Employee with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);

            } else {
                throw new EntityNotFoundException("Employee with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Employee with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }
}
