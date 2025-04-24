package uz.alifservice.specification.currency;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.currency.CurrencyCriteria;
import uz.alifservice.domain.currency.Currency;
import uz.alifservice.specification.GenericSpecification;

@RequiredArgsConstructor
public class CurrencySpecification extends GenericSpecification<Currency> {

    private final CurrencyCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Currency> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
