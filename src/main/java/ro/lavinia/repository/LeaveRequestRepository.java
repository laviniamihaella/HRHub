package ro.lavinia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Integer> {
}
