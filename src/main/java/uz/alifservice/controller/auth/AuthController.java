package uz.alifservice.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.dto.auth.auth.AuthDto;
import uz.alifservice.dto.auth.user.UserCrudDto;
import uz.alifservice.dto.auth.user.UserDto;
import uz.alifservice.dto.communication.sms.SmsResendDTO;
import uz.alifservice.dto.communication.sms.SmsVerificationDTO;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.mapper.auth.UserMapper;
import uz.alifservice.service.auth.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class AuthController {

    private final AuthService service;
    private final UserMapper userMapper;

    @RequestMapping(value = "/auth/registration", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registration(
            @Valid @RequestBody UserCrudDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return new ResponseEntity<>(userMapper.toDto(service.registration(dto, lang)), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/auth/registration/email-verification/{token}", method = RequestMethod.GET)
    public ResponseEntity<String> emailVerification(
            @PathVariable("token") String token,
            @RequestParam(value = "lang", defaultValue = "UZ") AppLanguage lang) {
        return new ResponseEntity<>(service.registrationEmailVerification(token, lang), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/registration/sms-verification", method = RequestMethod.POST)
    public ResponseEntity<UserDto> smsVerification(
            @Valid @RequestBody SmsVerificationDTO dto,
            @RequestParam(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return new ResponseEntity<>(userMapper.toDto(service.registrationSmsVerification(dto, lang)), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/registration/sms-verification-resend", method = RequestMethod.POST)
    public ResponseEntity<String> smsVerificationResend(
            @Valid @RequestBody SmsResendDTO dto,
            @RequestParam(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(service.registrationSmsVerificationResend(dto, lang));
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<UserDto> login(
            @Valid @RequestBody AuthDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return new ResponseEntity<>(userMapper.toDto(service.login(dto, lang)), HttpStatus.OK);
    }

    @RequestMapping(value = "/auth/reset-password", method = RequestMethod.POST)
    public ResponseEntity<AuthDto> resetPassword(
            @Valid @RequestBody AuthDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(service.resetPassword(dto, lang));
    }

    @RequestMapping(value = "/auth/reset-password-confirm", method = RequestMethod.POST)
    public ResponseEntity<String> resetPasswordConfirm(
            @Valid @RequestBody AuthDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return new ResponseEntity<>(service.resetPasswordConfirm(dto, lang) , HttpStatus.OK);
    }
}
