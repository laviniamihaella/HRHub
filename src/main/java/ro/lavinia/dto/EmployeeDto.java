package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.JobPosition;
import ro.lavinia.entity.LeaveRequest;

import java.time.LocalDate;
import java.util.List;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String address;

    private Integer salary;

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
