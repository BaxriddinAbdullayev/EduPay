package uz.alifservice.dto.communication.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsSendResponseDTO {
    private String id;
    private String message;
    private String status;
}
