package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;
import ro.lavinia.entity.User;

import java.time.LocalDate;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
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
