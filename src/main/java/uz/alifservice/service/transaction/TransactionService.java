package uz.alifservice.service.transaction;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.alifservice.collection.PaymentInfoCollection;
import uz.alifservice.criteria.transaction.TransactionCriteria;
import uz.alifservice.domain.transaction.Transaction;
import uz.alifservice.dto.transaction.TransactionCrudDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.transaction.TransactionMapper;
import uz.alifservice.repository.transaction.TransactionRepository;
import uz.alifservice.service.GenericCrudService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements GenericCrudService<Transaction, TransactionCrudDto, TransactionCriteria> {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Transaction get(Long id, AppLanguage lang) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> list(TransactionCriteria criteria, AppLanguage lang) {
        return repository.findAll(criteria.toSpecification(),
                PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(criteria.getDirection(), criteria.getSort())));
    }

    @Override
    @Transactional
    public Transaction create(TransactionCrudDto dto, AppLanguage lang) {
        return repository.save(mapper.fromCreateDto(dto));
    }

    @Override
    @Transactional
    public Transaction update(Long id, TransactionCrudDto dto, AppLanguage lang) {
        Transaction entity = get(id, lang);
        return repository.save(mapper.fromUpdate(dto, entity));
    }

    @Override
    @Transactional
    public void delete(Long id, AppLanguage lang) {
        Transaction entity = get(id, lang);
        entity.setDeleted(true);
    }

    @Transactional
    public Page<PaymentInfoCollection> paymentInfoReport(TransactionCriteria criteria) {
        return repository.paymentInfoReport(
                criteria.getFromDate(),
                criteria.getToDate(),
                PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(criteria.getDirection(), criteria.getSort()))
                );
    }
}
