package uz.alifservice.service;

import org.springframework.data.domain.Page;
import uz.alifservice.criteria.GenericCriteria;
import uz.alifservice.domain.Auditable;
import uz.alifservice.dto.GenericCrudDto;
import uz.alifservice.enums.AppLanguage;

public interface GenericCrudService<T extends Auditable<Long>, C extends GenericCrudDto, E extends GenericCriteria> {

    T get(Long id, AppLanguage lang);

    Page<T> list(E criteria, AppLanguage lang);

    T create(C dto, AppLanguage lang);

    T update(Long id, C dto, AppLanguage lang);

    void delete(Long id, AppLanguage lang);
}
