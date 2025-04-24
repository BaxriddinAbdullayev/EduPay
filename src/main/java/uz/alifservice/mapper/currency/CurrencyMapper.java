package uz.alifservice.mapper.currency;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.currency.Currency;
import uz.alifservice.dto.currency.CurrencyCrudDto;
import uz.alifservice.dto.currency.CurrencyDto;
import uz.alifservice.mapper.BaseCrudMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper extends BaseCrudMapper<Currency, CurrencyDto, CurrencyCrudDto> {

}
