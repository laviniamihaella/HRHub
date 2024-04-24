package ro.lavinia.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "department")

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL)
    private List<Employee> employeeList;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<JobPosition> jobPositions;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<LeaveRequest>leaveRequestList;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Attendance>attendanceList;


}
