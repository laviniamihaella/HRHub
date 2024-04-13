package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.entity.LeaveRequest;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String address;

    private Float salary;

    @JsonIgnore
    private Department department;

    @JsonIgnore
    private JobPosition jobPosition;

    @JsonManagedReference
    @JsonIgnore
    private List<LeaveRequest> leaveRequestList;

    @JsonManagedReference
    @JsonIgnore
    private List<Attendance> attendanceList;


}
