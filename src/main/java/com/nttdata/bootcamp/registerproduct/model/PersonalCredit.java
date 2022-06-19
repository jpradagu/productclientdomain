package com.nttdata.bootcamp.registerproduct.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Document(collection = "personalCredits")
public class PersonalCredit {
    @Id
    private String id;
    @NotNull
    private String code;
    @NotNull
    private BigDecimal amountGiven;
    @NotNull
    private BigDecimal amountPaid;
    @NotNull
    private Integer fees;
    @NotNull
    private Integer feesPaid;
    @NotNull
    private PersonalCustomer customer;
    @NotNull
    private TypeCredit typeCredit;
    @NotNull
    private Boolean state;
}
