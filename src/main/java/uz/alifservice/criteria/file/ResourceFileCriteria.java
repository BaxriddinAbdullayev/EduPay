package uz.alifservice.criteria.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.file.ResourceFile;
import uz.alifservice.specification.file.ResourceFileSpecification;

@Getter
@Setter
public class ResourceFileCriteria extends GenericCriteria {

    private String origenName;

    @Builder(builderMethodName = "childBuilder")
    public ResourceFileCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        super(id, page, size, direction, sort);
    }

    public Specification<ResourceFile> toSpecification() {
        return new ResourceFileSpecification(this);
    }
}
