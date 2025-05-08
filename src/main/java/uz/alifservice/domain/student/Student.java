package uz.alifservice.domain.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;

@Getter
@Setter
@Entity
@Table(name = "students")
public class Student extends Auditable<Long> {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "course", nullable = false)
    private Integer course;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "major", nullable = false)
    private String major;
}
