package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ro.lavinia.entity.Employee;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDto {

    private Integer id;

    private String name;

    private String description;

    private String status;

    private double budget;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonManagedReference
    @JsonIgnore
    private List<Employee> teamMembers;
}
