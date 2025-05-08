package uz.alifservice.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericCrudDto;
import uz.alifservice.dto.student.StudentDto;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionCrudDto extends GenericCrudDto {

    private StudentDto student;
    private BigDecimal amount;
    private String description;
}
