package uz.alifservice.dto.communication.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsAuthDTO {
    private String email;
    private String password;
}
