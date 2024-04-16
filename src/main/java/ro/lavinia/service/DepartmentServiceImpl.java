package ro.lavinia.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.entity.Department;
import ro.lavinia.exception.EntityNotFoundException;
import ro.lavinia.exception.FieldNotFoundException;
import ro.lavinia.mapper.DepartmentMapper;
import ro.lavinia.repository.DepartmentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl {
    private final DepartmentRepository departmentRepository;

    public ResponseEntity<?> save(DepartmentDto departmentDto) {
        try {

            if (departmentDto.getName() == null || departmentDto.getName().isEmpty() ||
                    departmentDto.getDescription() == null || departmentDto.getDescription().isEmpty()) {
                return new ResponseEntity<>("Department information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            Department department = DepartmentMapper.INSTANCE.DepartmentDtoToDepartmentEntity(departmentDto);
            departmentRepository.save(department);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while saving the attendance: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Department has been successfully saved.", HttpStatus.OK);
    }

    public ResponseEntity<?> getADepartmentById(Integer existingId) {
        try {
            Optional<Department> departmentOptional = departmentRepository.findById(existingId);
            if (departmentOptional.isPresent()) {
                Department department = departmentOptional.get();
                DepartmentDto departmentDto = DepartmentMapper.INSTANCE.DepartmentEntityToDepartmentDto(department);
                return new ResponseEntity<>(departmentDto, HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Department with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department with ID " + existingId + "  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAllDepartment() {
        try {
            List<Department> departmentList = departmentRepository.findAll();
            if (departmentList.isEmpty()) {
                throw new EntityNotFoundException("Department list  NOT Found.");
            } else {
                List<DepartmentDto> departmentDtoList = departmentList.stream().toList()
                        .stream().map(DepartmentMapper.INSTANCE::DepartmentEntityToDepartmentDto)
                        .toList();
                return new ResponseEntity<>(departmentDtoList, HttpStatus.OK);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department list  NOT Found.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updatePatch(Integer existingId, Map<String, Object> updatedDepartment) {

        try {

            var departmentOptional = departmentRepository.findById(existingId);
            if (departmentOptional.isEmpty()) {
                throw new EntityNotFoundException("Department with ID " + existingId + " not found");
            }
            Department department = departmentOptional.get();
            for (Map.Entry<String, Object> entry : updatedDepartment.entrySet()) {
                String key = entry.getKey().toLowerCase();
                switch (key) {
                    case "name":
                        department.setName((String) entry.getValue());
                        break;
                    case "description":
                        department.setDescription((String) entry.getValue());
                        break;
                    default:
                        throw new FieldNotFoundException("Unknown field: " + key);
                }
            }
            departmentRepository.save(department);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Department with ID " + existingId + " has been successfully updated with patched.", HttpStatus.OK);
    }

    public ResponseEntity<?> updatePut(Integer existingId, Department updatedDepartment) {
        try {

            var departmentOptional = departmentRepository.findById(existingId);
            if (departmentOptional.isEmpty()) {
                throw new EntityNotFoundException("Department with ID " + existingId + " not found");
            }
            Department existingDepartment = departmentOptional.get();

            existingDepartment.setName(updatedDepartment.getName());
            existingDepartment.setDescription(updatedDepartment.getDescription());

            if (existingDepartment.getName() == null || existingDepartment.getName().isEmpty() ||
                    existingDepartment.getDescription() == null || existingDepartment.getDescription().isEmpty()) {
                return new ResponseEntity<>("Department information is incomplete.", HttpStatus.BAD_REQUEST);
            }

            departmentRepository.save(existingDepartment);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Department with ID " + existingId + " has been successfully updated with put.", HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(Integer existingId) {
        try {
            Optional<Department> optionalProperty = departmentRepository.findById(existingId);
            if (optionalProperty.isPresent()) {
                departmentRepository.deleteById(existingId);
                return new ResponseEntity<>("Department with ID " + existingId + " has been successfully deleted.", HttpStatus.OK);
            } else {
                throw new EntityNotFoundException("Department with ID " + existingId + " NOT Found.");
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Department with ID " + existingId + " NOT Found.", HttpStatus.NOT_FOUND);
        }
    }
}
