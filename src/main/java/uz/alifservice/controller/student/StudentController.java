package uz.alifservice.controller.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.controller.GenericCrudController;
import uz.alifservice.criteria.student.StudentCriteria;
import uz.alifservice.domain.student.Student;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.student.StudentCrudDto;
import uz.alifservice.dto.student.StudentDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.student.StudentMapper;
import uz.alifservice.service.message.ResourceBundleService;
import uz.alifservice.service.student.StudentService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class StudentController implements GenericCrudController<StudentDto, StudentCrudDto, StudentCriteria> {

    private final StudentService service;
    private final StudentMapper mapper;
    private final ResourceBundleService bundleService;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<StudentDto>> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Student.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.get(id, lang)), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<Page<StudentDto>>> list(
            StudentCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(System.out::println);
        String messsage = Student.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(service.list(criteria, lang).map(mapper::toDto), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<StudentDto>> create(
            @RequestBody StudentCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Student.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("created", lang);
        return new ResponseEntity<>(AppResponse.success(mapper.toDto(service.create(dto, lang)), messsage), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/students/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<AppResponse<StudentDto>> edit(
            @PathVariable(
                    value = "id") Long id,
            @RequestBody StudentCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Student.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("updated", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.update(id, dto, lang)), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<AppResponse<Boolean>> delete(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.delete(id, lang);
        String messsage = Student.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("deleted", lang);
        return ResponseEntity.ok(AppResponse.success(true, messsage));
    }
}
