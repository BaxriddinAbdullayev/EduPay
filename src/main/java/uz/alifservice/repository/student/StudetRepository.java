package uz.alifservice.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.alifservice.domain.student.Student;

@Repository
public interface StudetRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}