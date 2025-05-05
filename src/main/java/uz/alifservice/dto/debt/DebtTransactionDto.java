package uz.alifservice.dto.debt;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;
import uz.alifservice.enums.DebtAction;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class DebtTransactionDto extends GenericDto {

    private DebtDto debt;
    private BigDecimal amount;
    private String description;
    private Date issueDate;
    private Date dueDate;
    private String fileHash;
    private DebtAction action;
}
