package uz.alifservice.dto.currency;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyDto extends GenericDto {

    private String code;
    private String name;
    private String symbol;
    private BigDecimal exchangeRate;
    private String iconUrl;
    private String baseCurrency;
}
