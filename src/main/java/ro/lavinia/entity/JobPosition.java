package ro.lavinia.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String responsibilities;

    @Column(nullable = false)
    private String requests;

    @OneToMany(mappedBy = "jobPosition", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}
