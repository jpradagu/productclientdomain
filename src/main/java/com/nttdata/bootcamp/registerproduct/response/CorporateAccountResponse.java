package com.nttdata.bootcamp.registerproduct.response;

import com.nttdata.bootcamp.registerproduct.model.Client;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CorporateAccountResponse {
  private String id;
  private String code;
  private String accountNumber;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date openingDate;
  private Boolean state;
  private BigDecimal amount;
  private List<Client> holders;
  private List<Client> signers;
  private CustomerResponse customer;
  private TypeAccount typeAccount;
}
