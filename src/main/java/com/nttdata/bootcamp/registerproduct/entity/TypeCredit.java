package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TypeCredit {
    @NotNull
    private String idCreditAccount;
    private String type;
    private BigDecimal interestRateMonth;
    private Boolean allowCompany;
    private Boolean allowPerson;
    private Boolean needCreditCard;
}
