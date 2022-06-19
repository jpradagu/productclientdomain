package com.nttdata.bootcamp.registerproduct.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TypeCredit {
    @NotNull
    private String id;
    private String type;
    private BigDecimal interestRateMonth;
    private Boolean allowCompany;
    private Boolean allowPerson;
    private Boolean needCreditCard;
}
