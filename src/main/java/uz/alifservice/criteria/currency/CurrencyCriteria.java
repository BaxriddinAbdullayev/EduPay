package uz.alifservice.criteria.currency;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.currency.Currency;
import uz.alifservice.specification.currency.CurrencySpecification;

@Getter
@Setter
public class CurrencyCriteria extends GenericCriteria {

    @Builder(builderMethodName = "childBuilder")
    public CurrencyCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<Currency> toSpecification() {
        return new CurrencySpecification(this);
    }
}
