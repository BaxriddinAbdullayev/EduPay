package uz.alifservice.dto.auth.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordConfirmDto {

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String newPassword;

    @NotBlank(message = "Confirm Code required")
    private String confirmCode;
}
