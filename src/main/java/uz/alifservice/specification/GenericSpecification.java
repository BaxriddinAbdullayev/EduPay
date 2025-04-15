package uz.alifservice.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    protected List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
        return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
        // Yuqoridagi ishlamasa quydagini ishlatish kerak
        // return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    public Predicate toPredicateParentId(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
        predicates.add(criteriaBuilder.isNull(root.get("parent")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}