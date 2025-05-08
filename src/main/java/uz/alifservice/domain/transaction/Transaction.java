package uz.alifservice.domain.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;
import uz.alifservice.domain.student.Student;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends Auditable<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;
}
