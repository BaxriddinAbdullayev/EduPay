package uz.alifservice.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.alifservice.config.security.CustomUserDetails;
import uz.alifservice.config.security.CustomUserDetailsService;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.domain.auth.User;
import uz.alifservice.dto.AppResponse;
import uz.alifservice.dto.auth.auth.*;
import uz.alifservice.dto.communication.sms.ResendVerificationDto;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.enums.GeneralStatus;
import uz.alifservice.enums.SmsType;
import uz.alifservice.exps.AppBadException;
import uz.alifservice.mapper.auth.UserMapper;
import uz.alifservice.repository.auth.RoleRepository;
import uz.alifservice.repository.auth.UserRepository;
import uz.alifservice.service.communication.email.EmailHistoryService;
import uz.alifservice.service.communication.email.EmailSendingService;
import uz.alifservice.service.communication.sms.SmsHistoryService;
import uz.alifservice.service.communication.sms.SmsSendService;
import uz.alifservice.service.message.ResourceBundleService;
import uz.alifservice.util.EmailUtil;
import uz.alifservice.util.JwtUtil;
import uz.alifservice.util.PhoneUtil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSendingService emailSendingService;
    private final RoleRepository roleRepository;
    private final ResourceBundleService bundleService;
    private final SmsSendService smsSendService;
    private final SmsHistoryService smsHistoryService;
    private final EmailHistoryService emailHistoryService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public AppResponse<?> registration(AuthDto dto, AppLanguage lang) {
        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getUsername());
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                userRepository.deleteRolesByUserId(user.getId());
                userRepository.delete(user);
            } else {
                throw new AppBadException(bundleService.getMessage("email.phone.exists", lang));
            }
        }

        Role roleUser = roleRepository.findByRoleNameAndActiveTrueAndDeletedFalse("ROLE_USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleUser);

        User entity = new User();
        entity.setFullName(dto.getFullName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setRoles(roleSet);
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        User user = userRepository.save(entity);

        String key = sendCodeForEmailOrPhone(user.getUsername(), SmsType.REGISTRATION, lang);
        return AppResponse.success(null, bundleService.getMessage("confirm.send." + key, lang));
    }

    public AppResponse<AuthVerificationResDto> registrationVerification(AuthVerificationReqDto dto, AppLanguage lang) {
        User user = getUserIfExists(dto.getUsername(), lang);
        checkUserStatusOrThrow(user.getStatus(), GeneralStatus.IN_REGISTRATION, lang);

        if (PhoneUtil.isPhone(dto.getUsername())) {
            smsHistoryService.check(dto.getUsername(), dto.getCode(), lang);
        } else if (EmailUtil.isEmail(dto.getUsername())) {
            emailHistoryService.check(dto.getUsername(), dto.getCode(), lang);
        }
        userRepository.changeStatus(user.getId(), GeneralStatus.ACTIVE);

        user.setPassword(null);
        user.setStatus(GeneralStatus.ACTIVE);
        AuthVerificationResDto resDto = new AuthVerificationResDto(
                jwtUtil.generateAccessToken(user.getId()),
                jwtUtil.generateRefreshToken(user.getId()),
                userMapper.toDto(user)
        );
        return AppResponse.success(resDto, bundleService.getMessage("verification.success", lang));
    }

    public AppResponse<?> registrationVerificationResend(ResendVerificationDto dto, AppLanguage lang) {
        User user = getUserIfExists(dto.getUsername(), lang);
        checkUserStatusOrThrow(user.getStatus(), GeneralStatus.IN_REGISTRATION, lang);
        String key = sendCodeForEmailOrPhone(user.getUsername(), SmsType.REGISTRATION, lang);
        return AppResponse.success(null, bundleService.getMessage(key + ".resend", lang));
    }

    public AppResponse<AuthVerificationResDto> login(LoginDto dto, AppLanguage lang) {
        User user = getUserIfExists(dto.getUsername(), lang);
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AppBadException(bundleService.getMessage("password.wrong", lang));
        }
        checkUserStatusOrThrow(user.getStatus(), GeneralStatus.ACTIVE, lang);

        user.setPassword(null);
        AuthVerificationResDto resDto = new AuthVerificationResDto(
                jwtUtil.generateAccessToken(user.getId()),
                jwtUtil.generateRefreshToken(user.getId()),
                userMapper.toDto(user)
        );
        return AppResponse.success(resDto, bundleService.getMessage("login.success", lang));
    }

    public AppResponse<?> resetPassword(ResetPasswordDto dto, AppLanguage lang) {
        User user = getUserIfExists(dto.getUsername(), lang);
        checkUserStatusOrThrow(user.getStatus(), GeneralStatus.ACTIVE, lang);
        String key = sendCodeForEmailOrPhone(user.getUsername(), SmsType.RESET_PASSWORD, lang);
        return AppResponse.success(null, bundleService.getMessage("confirm.send." + key, lang));
    }

    public AppResponse<?> resetPasswordConfirm(ResetPasswordConfirmDto dto, AppLanguage lang) {
        User user = getUserIfExists(dto.getUsername(), lang);
        checkUserStatusOrThrow(user.getStatus(), GeneralStatus.ACTIVE, lang);

        if (PhoneUtil.isPhone(dto.getUsername())) {
            smsHistoryService.check(dto.getUsername(), dto.getConfirmCode(), lang);
        } else if (EmailUtil.isEmail(dto.getUsername())) {
            emailHistoryService.check(dto.getUsername(), dto.getConfirmCode(), lang);
        } else {
            throw new AppBadException(bundleService.getMessage("email.phone.wrong.format", lang));
        }

        userRepository.updatePassword(user.getId(), bCryptPasswordEncoder.encode(dto.getNewPassword()));
        return AppResponse.success(null, bundleService.getMessage("reset.success", lang));
    }

    public AppResponse<AuthVerificationResDto> handleOAuth2Callback(String email, String name, String accessToken, String refreshToken, AppLanguage lang) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.createOrUpdateOAuth2User(email, name);
        User user = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(email).get();

        AuthVerificationResDto resDto = new AuthVerificationResDto(
                accessToken,
                refreshToken,
                userMapper.toDto(user)
        );
        return AppResponse.success(resDto, bundleService.getMessage("login.success", lang));
    }

    private User getUserIfExists(String username, AppLanguage lang) {
        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(username);
        if (optional.isEmpty()) {
            if (PhoneUtil.isPhone(username)) {
                throw new AppBadException(bundleService.getMessage("phone.not.found", lang));
            } else if (EmailUtil.isEmail(username)) {
                throw new AppBadException(bundleService.getMessage("email.not.found", lang));
            } else {
                throw new AppBadException(bundleService.getMessage("email.phone.wrong.format", lang));
            }
        }
        return optional.get();
    }

    private void checkUserStatusOrThrow(GeneralStatus userStatus, GeneralStatus status, AppLanguage lang) {
        if (!userStatus.equals(status)) {
            throw new AppBadException(bundleService.getMessage("status.wrong", lang));
        }
    }

    private String sendCodeForEmailOrPhone(String username, SmsType smsType, AppLanguage lang) {
        if (PhoneUtil.isPhone(username)) {
            smsSendService.sendRegistrationSms(username, smsType, lang);
            return "sms";
        } else if (EmailUtil.isEmail(username)) {
            emailSendingService.sendRegistrationEmail(username, smsType, lang);
            return "email";
        } else {
            throw new AppBadException(bundleService.getMessage("email.phone.incorrect.format", lang));
        }
    }

}
