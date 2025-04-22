package uz.alifservice.dto.auth.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthVerificationReqDto {

    @NotBlank(message = "Email or phone required")
    private String username;

    @NotBlank(message = "Code required")
    private String code;
}
