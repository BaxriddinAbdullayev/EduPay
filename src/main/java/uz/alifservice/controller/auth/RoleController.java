package uz.alifservice.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.controller.GenericCrudController;
import uz.alifservice.criteria.auth.RoleCriteria;
import uz.alifservice.dto.auth.role.RoleCrudDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.RoleMapper;
import uz.alifservice.service.auth.RoleService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class RoleController implements GenericCrudController<RoleDto, RoleCrudDto, RoleCriteria> {

    private final RoleService service;
    private final RoleMapper mapper;

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoleDto> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(mapper.toDto(service.get(id, lang)));
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<Page<RoleDto>> list(
            RoleCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.list(criteria, lang).map(mapper::toDto));
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<RoleDto> create(
            @RequestBody RoleCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return new ResponseEntity<>(mapper.toDto(service.create(dto, lang)), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public void edit(@PathVariable(
            value = "id") Long id,
                     @RequestBody RoleCrudDto dto,
                     @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.update(id, dto, lang);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.delete(id, lang);
        return ResponseEntity.ok(true);
    }
}
