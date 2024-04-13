package ro.lavinia.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Employee;


import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee EmployeeDtoToEmployeeEntity(EmployeeDto employeeDto);


    EmployeeDto EmployeeEntityToEmployeeDto(Employee employee);


}
