package ro.lavinia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
}
