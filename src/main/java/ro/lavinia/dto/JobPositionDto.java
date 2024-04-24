package ro.lavinia.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.lavinia.entity.Department;
import ro.lavinia.entity.Employee;

@Data
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
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
