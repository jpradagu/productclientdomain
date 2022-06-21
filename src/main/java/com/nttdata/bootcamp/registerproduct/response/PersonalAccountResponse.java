package com.nttdata.bootcamp.registerproduct.response;

import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class PersonalAccountResponse {
  private String id;
  private String code;
  private String accountNumber;
  private Date openingDate;
  private CustomerResponse customer;
  private BigDecimal amount;
  private TypeAccount account;
  private Boolean state;
}
