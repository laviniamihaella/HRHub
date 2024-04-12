package ro.lavinia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}
