package uz.alifservice.specification.debt;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.debt.DebtTransactionCriteria;
import uz.alifservice.domain.debt.DebtTransaction;
import uz.alifservice.specification.GenericSpecification;

@RequiredArgsConstructor
public class DebtTransactionSpecification extends GenericSpecification<DebtTransaction> {

    private final DebtTransactionCriteria criteria;

    @Override
    public Predicate toPredicate(Root<DebtTransaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
