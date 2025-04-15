package uz.alifservice.controller.auth;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.controller.GenericCrudController;
import uz.alifservice.criteria.auth.UserCriteria;
import uz.alifservice.dto.auth.user.UserCrudDto;
import uz.alifservice.dto.auth.user.UserDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.UserMapper;
import uz.alifservice.service.auth.UserService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class UserController implements GenericCrudController<UserDto, UserCrudDto, UserCriteria> {

    private final UserService service;
    private final UserMapper mapper;

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> get(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(mapper.toDto(service.get(id, lang)));
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<Page<UserDto>> list(
            UserCriteria criteria,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.list(criteria, lang).map(mapper::toDto));
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserDto> create(
            @RequestBody UserCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return new ResponseEntity<>(mapper.toDto(service.create(dto, lang)), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public void edit(
            @PathVariable("id") Long id,
            @RequestBody UserCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.update(id, dto, lang);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(
            @PathVariable(value = "id") Long id,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        service.delete(id, lang);
        return ResponseEntity.ok(true);
    }
}
