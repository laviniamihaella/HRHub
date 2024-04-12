package ro.lavinia.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.DepartmentDto;
import ro.lavinia.entity.Department;

@Mapper
public interface DepartmentMapper {


    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department DepartmentDtoToDepartmentEntity(DepartmentDto departmentDto);

    DepartmentDto DepartmentEntityToDepartmentDto(Department department);
}
