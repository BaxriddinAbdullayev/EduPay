package uz.alifservice.controller.transaction;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.collection.PaymentInfoCollection;
import uz.alifservice.controller.GenericCrudController;
import uz.alifservice.criteria.auth.RoleCriteria;
import uz.alifservice.criteria.student.StudentCriteria;
import uz.alifservice.criteria.transaction.TransactionCriteria;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.domain.student.Student;
import uz.alifservice.domain.transaction.Transaction;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.auth.role.RoleCrudDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.dto.student.StudentCrudDto;
import uz.alifservice.dto.student.StudentDto;
import uz.alifservice.dto.transaction.TransactionCrudDto;
import uz.alifservice.dto.transaction.TransactionDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.RoleMapper;
import uz.alifservice.mapper.student.StudentMapper;
import uz.alifservice.mapper.transaction.TransactionMapper;
import uz.alifservice.service.auth.RoleService;
import uz.alifservice.service.message.ResourceBundleService;
import uz.alifservice.service.student.StudentService;
import uz.alifservice.service.transaction.TransactionService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class TransactionController implements GenericCrudController<TransactionDto, TransactionCrudDto, TransactionCriteria> {

    private final TransactionService service;
    private final TransactionMapper mapper;
    private final ResourceBundleService bundleService;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<TransactionDto>> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.get(id, lang)), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<Page<TransactionDto>>> list(
            TransactionCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(service.list(criteria, lang).map(mapper::toDto), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<TransactionDto>> create(
            @RequestBody TransactionCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("created", lang);
        return new ResponseEntity<>(AppResponse.success(mapper.toDto(service.create(dto, lang)), messsage), HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<AppResponse<TransactionDto>> edit(
            @PathVariable(
                    value = "id") Long id,
            @RequestBody TransactionCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("updated", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.update(id, dto, lang)), messsage));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<AppResponse<Boolean>> delete(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.delete(id, lang);
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("deleted", lang);
        return ResponseEntity.ok(AppResponse.success(true, messsage));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/transactions/payment-report", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<Page<PaymentInfoCollection>>> paymentInfoReport(
            TransactionCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Transaction.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(service.paymentInfoReport(criteria), messsage));
    }
}