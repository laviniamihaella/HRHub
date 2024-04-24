package ro.lavinia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.Attendance;
import ro.lavinia.entity.Employee;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployee(Employee employee);
}
