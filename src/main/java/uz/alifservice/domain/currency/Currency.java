package uz.alifservice.domain.currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "currencies")
public class Currency extends Auditable<Long> {

    @Column(name = "code", nullable = false, unique = true, length = 3)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "exchange_rate", nullable = false, precision = 19, scale = 4)
    private BigDecimal exchangeRate;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "base_currency")
    private String baseCurrency;
}
