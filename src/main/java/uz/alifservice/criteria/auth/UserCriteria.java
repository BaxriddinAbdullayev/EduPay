package uz.alifservice.criteria.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.auth.User;
import uz.alifservice.specification.auth.UserSpecification;

@Getter
@Setter
public class UserCriteria extends GenericCriteria {

    @Builder(builderMethodName = "childBuilder")
    public UserCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<User> toSpecification() {
        return new UserSpecification(this);
    }

}
