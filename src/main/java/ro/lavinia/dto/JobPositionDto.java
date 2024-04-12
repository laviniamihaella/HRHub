package ro.lavinia.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;

@Data
public class JobPositionDto {


    private Integer id;

    private String name;

    private String description;

    private String responsibilities;

    private String requests;

    @JsonIgnore
    private Employee employee;

    @JsonIgnore
    private Department department;
}
