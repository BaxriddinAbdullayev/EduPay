package uz.alifservice.domain.communication.email;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;
import uz.alifservice.enums.SmsType;

@Getter
@Setter
@Entity
@Table(name = "email_history")
public class EmailHistory extends Auditable<Long> {

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_type")
    private SmsType emailType;

    @Column(name = "attempt_count")
    private Integer attemptCount;
}
