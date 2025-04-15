package uz.alifservice.service;

import org.springframework.data.domain.Page;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.enums.AppLanguage;

public interface GenericService<T, E extends GenericCriteria> {

    T get(Long id, AppLanguage lang);

    Page<T> list(E criteria, AppLanguage lang);
}
