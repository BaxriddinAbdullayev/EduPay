package uz.alifservice.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.registration(dto, lang));
    }

    @RequestMapping(value = "/auth/registration/verification", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<AuthVerificationResDto>> verification(
            @Valid @RequestBody AuthVerificationReqDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.registrationVerification(dto, lang));
    }

    @RequestMapping(value = "/auth/registration/verification-resend", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> smsVerificationResend(
            @Valid @RequestBody ResendVerificationDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.registrationVerificationResend(dto, lang));
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<AuthVerificationResDto>> login(
            @Valid @RequestBody LoginDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.login(dto, lang));
    }

    @RequestMapping(value = "/auth/reset-password", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> resetPassword(
            @Valid @RequestBody ResetPasswordDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(service.resetPassword(dto, lang));
    }

    @RequestMapping(value = "/auth/reset-password-confirm", method = RequestMethod.POST)
    public ResponseEntity<AppResponse<?>> resetPasswordConfirm(
            @Valid @RequestBody ResetPasswordConfirmDto dto,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.resetPasswordConfirm(dto, lang));
    }

    @GetMapping("/auth/oauth2/callback")
    public ResponseEntity<AppResponse<AuthVerificationResDto>> oauth2Callback(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam("accessToken") String accessToken,
            @RequestParam("refreshToken") String refreshToken,
            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok(service.handleOAuth2Callback(email, name, accessToken, refreshToken, lang));
    }
}
