package uz.alifservice.criteria.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.specification.auth.RoleSpecification;

@Getter
@Setter
public class RoleCriteria extends GenericCriteria {

    @Builder(builderMethodName = "childBuilder")
    public RoleCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<Role> toSpecification() {
        return new RoleSpecification(this);
    }

}
