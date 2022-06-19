package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "corporateCreditCards")
public class CorporateCreditCard {
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
    private PersonalCustomer customer;
    private TypeCreditCard typeCreditCard;
    private boolean state;
}
