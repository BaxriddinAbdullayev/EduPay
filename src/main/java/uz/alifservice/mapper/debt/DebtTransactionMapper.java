package uz.alifservice.mapper.debt;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.debt.DebtTransaction;
import uz.alifservice.dto.debt.DebtTransactionCrudDto;
import uz.alifservice.dto.debt.DebtTransactionDto;
import uz.alifservice.mapper.BaseCrudMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        DebtMapper.class
})
public interface DebtTransactionMapper extends BaseCrudMapper<DebtTransaction, DebtTransactionDto, DebtTransactionCrudDto> {

}
