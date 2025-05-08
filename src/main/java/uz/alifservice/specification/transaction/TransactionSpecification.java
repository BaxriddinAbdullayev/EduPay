package uz.alifservice.specification.transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.transaction.TransactionCriteria;
import uz.alifservice.domain.transaction.Transaction;
import uz.alifservice.specification.GenericSpecification;

import java.util.Objects;

@RequiredArgsConstructor
public class TransactionSpecification extends GenericSpecification<Transaction> {

    private final TransactionCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        if (!Objects.isNull(criteria.getStudentId())) {
            predicates.add(criteriaBuilder.equal(root.get("student").get("id"), criteria.getStudentId()));
        }

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
