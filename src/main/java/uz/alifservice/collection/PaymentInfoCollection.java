package uz.alifservice.collection;

import java.math.BigDecimal;

public interface PaymentInfoCollection {

    Long getId();
    String getFirstName();
    String getLastName();
    BigDecimal getTotalAmount();
    Integer getTransactionCount();
}
