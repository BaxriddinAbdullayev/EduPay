package uz.alifservice.dto.student;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;

@Getter
@Setter
public class StudentDto extends GenericDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer course;
    private String groupName;
    private String major;
}
