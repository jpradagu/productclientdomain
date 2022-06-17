package com.nttdata.bootcamp.productclientdomain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TypeCreditCard {
    @NotNull
    private String idCreditCard;
    @NotNull
    private String type;
    @NotNull
    private BigDecimal interestRateMonth;
    @NotNull
    private Boolean allowCompany;
    @NotNull
    private Boolean allowPerson;
}
