package uz.alifservice.dto.student;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericCrudDto;

@Getter
@Setter
public class StudentCrudDto extends GenericCrudDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Integer course;
    private String groupName;
    private String major;
}
