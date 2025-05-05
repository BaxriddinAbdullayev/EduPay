package uz.alifservice.criteria.debt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.debt.DebtTransaction;
import uz.alifservice.specification.debt.DebtTransactionSpecification;

@Getter
@Setter
public class DebtTransactionCriteria extends GenericCriteria {

    @Builder(builderMethodName = "childBuilder")
    public DebtTransactionCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<DebtTransaction> toSpecification() {
        return new DebtTransactionSpecification(this);
    }
}
