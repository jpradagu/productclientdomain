package com.nttdata.bootcamp.productclientdomain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "customerCreditCards")
public class CustomerCreditCard {
    @Id
    private String id;
    private String code;
    private String creditCardNumber;
    private BigDecimal limitAmount;
    private BigDecimal usedAmount;
    private int fees;
    private int feesPaid;
    private int cutoffDate;
    private int limitDate;
    private LocalDateTime openingDate;
    private LocalDateTime deliveryDate;
    private Customer client;
    private TypeCreditCard typeCreditCard;
    private boolean state;
}
