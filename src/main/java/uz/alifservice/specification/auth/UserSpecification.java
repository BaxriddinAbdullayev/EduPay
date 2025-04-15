package uz.alifservice.specification.auth;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.auth.UserCriteria;
import uz.alifservice.domain.auth.User;
import uz.alifservice.specification.GenericSpecification;

@RequiredArgsConstructor
public class UserSpecification extends GenericSpecification<User> {

    private final UserCriteria criteria;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
