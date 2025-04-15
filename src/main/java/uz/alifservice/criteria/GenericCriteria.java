package uz.alifservice.criteria;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class GenericCriteria implements Serializable {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = Integer.MAX_VALUE;
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;
    private static final String DEFAULT_SORT_ORDER = "id";

    protected Long id;
    protected Integer page;
    protected Integer size;
    protected Sort.Direction direction;
    protected String sort;

    public GenericCriteria(Long id, Integer page, Integer size, Sort.Direction direction, String sort) {
        this.id = id;
        this.page = page != null ? page : DEFAULT_PAGE_NUMBER;
        this.size = size != null ? size : DEFAULT_PAGE_SIZE;
        this.direction = direction != null ? direction : DEFAULT_SORT_DIRECTION;
        this.sort = sort != null ? sort : DEFAULT_SORT_ORDER;
    }
}
