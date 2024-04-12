package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.entity.LeaveRequest;

import java.util.List;

@Data
public class DepartmentDto {
    private Integer id;

    private String name;

    private String description;

    @JsonManagedReference // Marks this side of the relationship as the "forward" side
    @JsonIgnore
    private List<Employee> employeeList;

    @JsonManagedReference // Marks this side of the relationship as the "forward" side
    @JsonIgnore
    private List<JobPosition> jobPositionList;

    @JsonManagedReference // Marks this side of the relationship as the "forward" side
    @JsonIgnore
    private List<LeaveRequest>leaveRequestList;

    @JsonManagedReference // Marks this side of the relationship as the "forward" side
    @JsonIgnore
    private List<Attendance>attendanceList;

}
