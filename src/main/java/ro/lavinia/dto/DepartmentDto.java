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

    @JsonManagedReference
    @JsonIgnore
    private List<Employee> employeeList;

    @JsonManagedReference
    @JsonIgnore
    private List<JobPosition> jobPositionList;

    @JsonManagedReference
    @JsonIgnore
    private List<LeaveRequest>leaveRequestList;

    @JsonManagedReference
    @JsonIgnore
    private List<Attendance>attendanceList;

}
