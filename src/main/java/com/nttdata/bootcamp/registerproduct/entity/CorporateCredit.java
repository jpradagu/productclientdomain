package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "corporateCredits")
public class CorporateCredit {

    @Id
    private String id;
    private String code;
    private BigDecimal amountGiven;
    private BigDecimal amountPaid;
    private int fees;
    private int feesPaid;
    private PersonalCustomer customer;
    private TypeCredit typeCredit;
    private boolean state;

}
