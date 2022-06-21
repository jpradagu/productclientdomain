package com.nttdata.bootcamp.registerproduct.service.external.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

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
