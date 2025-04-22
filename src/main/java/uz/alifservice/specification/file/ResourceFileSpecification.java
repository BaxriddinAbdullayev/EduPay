package uz.alifservice.specification.file;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import uz.alifservice.criteria.file.ResourceFileCriteria;
import uz.alifservice.domain.file.ResourceFile;
import uz.alifservice.specification.GenericSpecification;

import java.util.Objects;

@RequiredArgsConstructor
public class ResourceFileSpecification extends GenericSpecification<ResourceFile> {

    private final ResourceFileCriteria criteria;

    @Override
    public Predicate toPredicate(Root<ResourceFile> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        if (!Objects.isNull(criteria.getOrigenName())) {
            predicates.add(criteriaBuilder.equal(root.get("origenName"), criteria.getOrigenName()));
        }
        predicates.add(criteriaBuilder.equal(root.get("deleted"), false));

        return query
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
                .getRestriction();
    }
}
