package uz.alifservice.service.currency;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.alifservice.criteria.currency.CurrencyCriteria;
import uz.alifservice.domain.currency.Currency;
import uz.alifservice.dto.currency.CurrencyCrudDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.currency.CurrencyMapper;
import uz.alifservice.repository.currency.CurrencyRepository;
import uz.alifservice.service.GenericCrudService;

@Service
@RequiredArgsConstructor
public class CurrencyService implements GenericCrudService<Currency, CurrencyCrudDto, CurrencyCriteria> {

    private final CurrencyRepository repository;
    private final CurrencyMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Currency get(Long id, AppLanguage lang) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Currency> list(CurrencyCriteria criteria, AppLanguage lang) {
        return repository.findAll(criteria.toSpecification(),
                PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(criteria.getDirection(), criteria.getSort())));
    }

    @Override
    @Transactional
    public Currency create(CurrencyCrudDto dto, AppLanguage lang) {
        return repository.save(mapper.fromCreateDto(dto));
    }

    @Override
    @Transactional
    public Currency update(Long id, CurrencyCrudDto dto, AppLanguage lang) {
        Currency entity = get(id, lang);
        return repository.save(mapper.fromUpdate(dto, entity));
    }

    @Override
    @Transactional
    public void delete(Long id, AppLanguage lang) {
        Currency entity = get(id, lang);
        entity.setDeleted(true);
    }
}
