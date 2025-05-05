package uz.alifservice.service.auth;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.alifservice.criteria.auth.UserCriteria;
import uz.alifservice.domain.auth.User;
import uz.alifservice.dto.auth.user.UserCrudDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.UserMapper;
import uz.alifservice.repository.auth.UserRepository;
import uz.alifservice.service.GenericCrudService;

@Service
@RequiredArgsConstructor
public class UserService implements GenericCrudService<User, UserCrudDto, UserCriteria> {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public User get(Long id, AppLanguage lang) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> list(UserCriteria criteria, AppLanguage lang) {
        return repository.findAll(criteria.toSpecification(),
                PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(criteria.getDirection(), criteria.getSort())));
    }

    @Override
    @Transactional
    public User create(UserCrudDto dto, AppLanguage lang) {
        return repository.save(mapper.fromCreateDto(dto));
    }

    @Override
    @Transactional
    public User update(Long id, UserCrudDto dto, AppLanguage lang) {
        User entity = get(id, lang);
        return repository.save(mapper.fromUpdate(dto, entity));
    }

    @Override
    @Transactional
    public void delete(Long id, AppLanguage lang) {
        User entity = get(id, lang);
        entity.setDeleted(true);
    }
}
