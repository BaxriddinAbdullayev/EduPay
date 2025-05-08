package uz.alifservice.mapper.student;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.student.Student;
import uz.alifservice.dto.student.StudentCrudDto;
import uz.alifservice.dto.student.StudentDto;
import uz.alifservice.mapper.BaseCrudMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper extends BaseCrudMapper<Student, StudentDto, StudentCrudDto> {

}
