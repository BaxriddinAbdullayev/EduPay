package uz.alifservice.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.controller.GenericCrudController;
import uz.alifservice.criteria.auth.RoleCriteria;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.auth.role.RoleCrudDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.RoleMapper;
import uz.alifservice.service.auth.RoleService;
import uz.alifservice.service.message.ResourceBundleService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class RoleController implements GenericCrudController<RoleDto, RoleCrudDto, RoleCriteria> {

    private final RoleService service;
    private final RoleMapper mapper;
    private final ResourceBundleService bundleService;

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<RoleDto>> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Role.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.get(id, lang)), messsage));
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<AppResponse<Page<RoleDto>>> list(
            RoleCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Role.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("retrieved", lang);
        return ResponseEntity.ok(AppResponse.success(service.list(criteria, lang).map(mapper::toDto), messsage));
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<RoleDto>> create(
            @RequestBody RoleCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Role.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("created", lang);
        return new ResponseEntity<>(AppResponse.success(mapper.toDto(service.create(dto, lang)), messsage), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<AppResponse<RoleDto>> edit(
            @PathVariable(
                    value = "id") Long id,
            @RequestBody RoleCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        String messsage = Role.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("updated", lang);
        return ResponseEntity.ok(AppResponse.success(mapper.toDto(service.update(id, dto, lang)), messsage));
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<AppResponse<Boolean>> delete(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.delete(id, lang);
        String messsage = Role.class.getSimpleName() + " " + bundleService.getSuccessCrudMessage("deleted", lang);
        return ResponseEntity.ok(AppResponse.success(true, messsage));
    }
}
