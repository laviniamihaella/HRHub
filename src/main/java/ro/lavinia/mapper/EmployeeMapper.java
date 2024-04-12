package ro.lavinia.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.lavinia.dto.EmployeeDto;
import ro.lavinia.entity.Employee;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee EmployeeDtoToEmployeeEntity(EmployeeDto employeeDto);

    EmployeeDto EmployeeEntityToEmployeeDto(Employee employee);

}