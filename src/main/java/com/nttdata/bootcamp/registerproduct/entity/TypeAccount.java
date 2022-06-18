package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
