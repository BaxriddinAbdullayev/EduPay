package uz.alifservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.enums.AppLanguage;

public interface GenericController<D, C> {

    ResponseEntity<AppResponse<D>> get(@PathVariable(value = "id") Long id, @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang);

    ResponseEntity<AppResponse<Page<D>>> list(C criteria, @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang);
}
