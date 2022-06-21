package com.nttdata.bootcamp.registerproduct.service.external.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

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
