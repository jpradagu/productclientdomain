package com.nttdata.bootcamp.registerproduct.response;

import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeCredit;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreditResponse {
  private String id;
  private String code;
  private BigDecimal amountGiven;
  private BigDecimal amountPaid;
  private Integer fees;
  private Integer feesPaid;
  private CustomerResponse customer;
  private TypeCredit typeCreditId;
  private Boolean state;
}
