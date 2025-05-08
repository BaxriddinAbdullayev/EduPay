package uz.alifservice.criteria.transaction;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.transaction.Transaction;
import uz.alifservice.specification.transaction.TransactionSpecification;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionCriteria extends GenericCriteria {

    private Long studentId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    @Builder(builderMethodName = "childBuilder")
    public TransactionCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<Transaction> toSpecification() {
        return new TransactionSpecification(this);
    }

}
