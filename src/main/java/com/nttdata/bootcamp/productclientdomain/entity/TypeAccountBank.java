package com.nttdata.bootcamp.productclientdomain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TypeAccountBank {
    private String idAccountType;
    private String type;
    private BigDecimal maintenanceCommission;
    private BigDecimal transactionCommission;
    private BigDecimal minimumOpeningAmount;
    private int numLimitMovements;
    private Boolean allowCompany;
    private Boolean allowPerson;
    private int dayMovement;
    private Boolean needCreditCard;
}
