package uz.alifservice.mapper.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.transaction.Transaction;
import uz.alifservice.dto.transaction.TransactionCrudDto;
import uz.alifservice.dto.transaction.TransactionDto;
import uz.alifservice.mapper.BaseCrudMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper extends BaseCrudMapper<Transaction, TransactionDto, TransactionCrudDto> {

}
