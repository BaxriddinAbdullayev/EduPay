package uz.alifservice.specification.student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.student.StudentCriteria;
import uz.alifservice.domain.student.Student;
import uz.alifservice.specification.GenericSpecification;

@RequiredArgsConstructor
public class StudentSpecification extends GenericSpecification<Student> {

    private final StudentCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
