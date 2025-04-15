package uz.alifservice.domain.communication.sms;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;
import uz.alifservice.enums.SmsType;

@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistory extends Auditable<Long> {

    @Column(name = "phone")
    private String phone;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "sms_type")
    private SmsType type;

    @Column(name = "attempt_count")
    private Integer attemptCount;

}
