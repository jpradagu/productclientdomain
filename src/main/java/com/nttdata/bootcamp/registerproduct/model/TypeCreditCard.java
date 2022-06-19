package com.nttdata.bootcamp.registerproduct.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TypeCreditCard {
    @NotNull
    private String id;
    @NotNull
    private String type;
    @NotNull
    private BigDecimal interestRateMonth;
    @NotNull
    private Boolean allowCompany;
    @NotNull
    private Boolean allowPerson;
}
