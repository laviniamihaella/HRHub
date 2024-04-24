package ro.lavinia.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.lavinia.entity.Employee;

import java.time.LocalDate;
import java.util.List;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private Integer id;

    private String name;

    private String description;

    private String status;

    private Integer budget;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonManagedReference
    @JsonIgnore
    private List<Employee> teamMembers;
}
