package uz.alifservice.dto.communication.sms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResendVerificationDto {

    @NotBlank(message = "Email or Phone required")
    private String username;
}
