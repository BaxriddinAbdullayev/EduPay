package uz.alifservice.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.auth.auth.*;
import uz.alifservice.dto.communication.sms.ResendVerificationDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.service.auth.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${app.api.base-path}", produces = "application/json")
public class AuthController {

    private final AuthService service;

    @RequestMapping(value = "/auth/registration", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> registration(
            @Valid @RequestBody AuthDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.registration(dto, lang));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/auth/registration/verification", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<AuthVerificationResDto>> verification(
            @Valid @RequestBody AuthVerificationReqDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.registrationVerification(dto, lang));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/auth/registration/verification-resend", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> smsVerificationResend(
            @Valid @RequestBody ResendVerificationDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.registrationVerificationResend(dto, lang));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<AuthVerificationResDto>> login(
            @Valid @RequestBody LoginDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.login(dto, lang));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/auth/reset-password", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> resetPassword(
            @Valid @RequestBody ResetPasswordDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok().body(service.resetPassword(dto, lang));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/auth/reset-password-confirm", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> resetPasswordConfirm(
            @Valid @RequestBody ResetPasswordConfirmDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.resetPasswordConfirm(dto, lang));
    }

    @RequestMapping(value = "/auth/oauth2/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(
            @RequestBody IdTokenRequest dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang
    ) {
        return ResponseEntity.ok(service.processOAuth2User(dto, lang));
    }
}
