package ro.lavinia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.JobPosition;


public interface JobPositionRepository extends JpaRepository<JobPosition,Integer> {
}
