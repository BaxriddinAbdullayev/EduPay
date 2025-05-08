package uz.alifservice.service.student;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.alifservice.criteria.student.StudentCriteria;
import uz.alifservice.domain.student.Student;
import uz.alifservice.dto.student.StudentCrudDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.student.StudentMapper;
import uz.alifservice.repository.student.StudetRepository;
import uz.alifservice.service.GenericCrudService;

@Service
@RequiredArgsConstructor
public class StudentService implements GenericCrudService<Student, StudentCrudDto, StudentCriteria> {

    private final StudetRepository repository;
    private final StudentMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Student get(Long id, AppLanguage lang) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> list(StudentCriteria criteria, AppLanguage lang) {
        return repository.findAll(criteria.toSpecification(),
                PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(criteria.getDirection(), criteria.getSort())));
    }

    @Override
    @Transactional
    public Student create(StudentCrudDto dto, AppLanguage lang) {
        return repository.save(mapper.fromCreateDto(dto));
    }

    @Override
    @Transactional
    public Student update(Long id, StudentCrudDto dto, AppLanguage lang) {
        Student entity = get(id, lang);
        return repository.save(mapper.fromUpdate(dto, entity));
    }

    @Override
    @Transactional
    public void delete(Long id, AppLanguage lang) {
        Student entity = get(id, lang);
        entity.setDeleted(true);
    }
}
