package uz.alifservice.service.auth;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.domain.auth.User;
import uz.alifservice.dto.auth.auth.AuthDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.dto.auth.user.UserCrudDto;
import uz.alifservice.dto.communication.sms.SmsResendDTO;
import uz.alifservice.dto.communication.sms.SmsVerificationDTO;
import uz.alifservice.enums.AppLanguage;
import uz.alifservice.enums.GeneralStatus;
import uz.alifservice.exps.AppBadException;
import uz.alifservice.mapper.auth.RoleMapper;
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
    private final RoleMapper roleMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleService roleService;
    private final EmailSendingService emailSendingService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final ResourceBundleService bundleService;
    private final SmsSendService smsSendService;
    private final SmsHistoryService smsHistoryService;
    private final EmailHistoryService emailHistoryService;
    private final JwtUtil jwtUtil;

    public User registration(UserCrudDto dto, AppLanguage lang) {

        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getUsername());
        if (optional.isPresent()) {
            User user = optional.get();
            if (user.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                userRepository.deleteRolesByUserId(user.getId());
                userRepository.delete(user);
                // send sms/email
            } else {
                throw new AppBadException(bundleService.getMessage("email.phone.exists", lang));
            }
        }

        Role roleUser = roleRepository.findByRoleNameAndActiveTrueAndDeletedFalse("ROLE_USER");
        RoleDto roleDto = roleMapper.toDto(roleUser);
        Set<RoleDto> roleSet = new HashSet<>();
        roleSet.add(roleDto);

        dto.setRoles(roleSet);
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        dto.setStatus(GeneralStatus.IN_REGISTRATION);
        User entity = userRepository.save(userMapper.fromCreateDto(dto));

        // send
        if (PhoneUtil.isPhone(dto.getUsername())) {
            smsSendService.sendRegistrationSms(dto.getUsername(), lang);
        } else if (EmailUtil.isEmail(dto.getUsername())) {
            emailSendingService.sendRegistrationEmail(entity.getUsername(), entity.getId(), lang);
        }

        return entity;
    }

    public String registrationEmailVerification(String token, AppLanguage lang) {
        try {
            Long userId = Long.valueOf(jwtUtil.extractUsername(token));
            User user = userService.get(userId, lang);
            if (user.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                userRepository.changeStatus(userId, GeneralStatus.ACTIVE);
                return bundleService.getMessage("verification.finished", lang);
            }
        } catch (JwtException e) {
        }
        throw new AppBadException(bundleService.getMessage("verification.failed", lang));
    }

    public User login(AuthDto dto, AppLanguage lang) {

        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("username.password.wrong", lang));
        }

        User user = optional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AppBadException(bundleService.getMessage("username.password.wrong", lang));
        }

        if (!user.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(bundleService.getMessage("status.wrong", lang));
        }
        return user;
    }

    public User registrationSmsVerification(SmsVerificationDTO dto, AppLanguage lang) {
        // 998931051739
        // 12345

        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        User user = optional.get();
        if (!user.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }

        // code check
        smsHistoryService.check(dto.getPhone(), dto.getCode(), lang);

        // ACTIVE
        userRepository.changeStatus(user.getId(), GeneralStatus.ACTIVE);
        return user;
    }

    public String registrationSmsVerificationResend(SmsResendDTO dto, AppLanguage lang) {

        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        User user = optional.get();
        if (!user.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }

        smsSendService.sendRegistrationSms(dto.getPhone(), lang);
        return bundleService.getMessage("sms.resend", lang);
    }

    public AuthDto resetPassword(AuthDto dto, AppLanguage lang) {

        // check
        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("profile.not.found", lang));
        }

        User user = optional.get();
        if (!user.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(bundleService.getMessage("status.wrong", lang));
        }

        // send
        if (PhoneUtil.isPhone(dto.getUsername())) {
            smsSendService.sendResetPasswordSms(dto.getUsername(), lang);
        } else if (EmailUtil.isEmail(dto.getUsername())) {
            emailSendingService.sendResetPasswordEmail(dto.getUsername(), lang);
        }

        return dto;
    }

    public String resetPasswordConfirm(AuthDto dto, AppLanguage lang) {

        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        User user = optional.get();
        if (!user.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(bundleService.getMessage("status.wrong", lang));
        }

        // check
        if (PhoneUtil.isPhone(dto.getUsername())) {
            smsHistoryService.check(dto.getUsername(), dto.getConfirmCode(), lang);
        } else if (EmailUtil.isEmail(dto.getUsername())) {
            emailHistoryService.check(dto.getUsername(), dto.getConfirmCode(), lang);
        }
        // update
        userRepository.updatePassword(user.getId(), bCryptPasswordEncoder.encode(dto.getPassword()));
        // return
        return bundleService.getMessage("reset.password.success", lang);
    }
}
