package uz.alifservice.criteria.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.student.Student;
import uz.alifservice.specification.student.StudentSpecification;

@Getter
@Setter
public class StudentCriteria extends GenericCriteria {

    @Builder(builderMethodName = "childBuilder")
    public StudentCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<Student> toSpecification() {
        return new StudentSpecification(this);
    }

}
