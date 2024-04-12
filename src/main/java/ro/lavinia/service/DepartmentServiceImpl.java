package ro.lavinia.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.entity.Department;
import ro.lavinia.mapper.DepartmentMapper;
import ro.lavinia.repository.DepartmentRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl {
    private final DepartmentRepository departmentRepository;

    public void save(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.INSTANCE.DepartmentDtoToDepartmentEntity(departmentDto);
        departmentRepository.save(department);
    }

    public Optional<DepartmentDto> getADepartmentById(Integer id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.map(DepartmentMapper.INSTANCE::DepartmentEntityToDepartmentDto);

    }
    public void deleteById(Integer id) {
        Optional<Department> optionalProperty = departmentRepository.findById(id);
        if (optionalProperty.isPresent()) {
            departmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("The id for department is not valid");
        }
    }

    public List<DepartmentDto> getAllDepartment() {
        try {
            return departmentRepository.findAll().stream()
                    .map(DepartmentMapper.INSTANCE::DepartmentEntityToDepartmentDto)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get list of departments: " + e.getMessage());
        }

    }

    public void updatePatch(Integer existingId, Map<String, Object> attendance) {
        Department existingDepartment = departmentRepository.findById(existingId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));
                attendance.forEach((key, value) -> {
            switch (key) {
                case "Name":
                    if (value instanceof Date) {
                        existingDepartment.setName(existingDepartment.getName());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'Name'");
                    }
                    break;
                case "Description":
                    if (value instanceof String) {
                        existingDepartment.setDescription(existingDepartment.getDescription());
                    } else {
                        throw new IllegalArgumentException("Invalid value for 'ArrivalTime'");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + key);
            }
        });

        departmentRepository.save(existingDepartment);
    }

    public void updatePut(Integer existingId, DepartmentDto departmentDto) {
        var attendanceOptional = departmentRepository.findById(existingId);
        if (attendanceOptional.isEmpty()) {
            throw new RuntimeException("Attendance NOT Found");
        }
        Department existingDepartment = attendanceOptional.get();

        existingDepartment.setName(departmentDto.getName());
        existingDepartment.setDescription(departmentDto.getDescription());
        departmentRepository.save(existingDepartment);
    }
}
