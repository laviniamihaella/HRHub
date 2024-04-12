package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;

import java.time.LocalDate;

@Data
public class LeaveRequestDto {

    private Integer id;

    private LocalDate startDate ;

    private LocalDate endDate;

    private String reason;

    private String Status;

    @JsonBackReference
    @JsonIgnore
    private Employee employee;

    @JsonBackReference
    @JsonIgnore
    private Department department;

}
