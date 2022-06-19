package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TypeAccount {
    @NotNull
    private String id;
    private String type;
    private BigDecimal maintenanceCommission;
    private BigDecimal transactionCommission;
    private BigDecimal minimumOpeningAmount;
    private Integer numLimitMovements;
    private Boolean allowCompany;
    private Boolean allowPerson;
    private Integer dayMovement;
    private Boolean needCreditCard;
}
