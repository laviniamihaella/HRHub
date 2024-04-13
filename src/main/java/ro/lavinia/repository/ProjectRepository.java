package ro.lavinia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.lavinia.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
