package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Document(collection = "personalAccounts")
public class PersonalAccount {
  @Id
  private String id;
  @NotNull
  private String code;
  @NotNull
  private String accountNumber;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date openingDate;
  @NotNull
  private String customerId;
  @NotNull
  private String typeAccountId;
  @NotNull
  private BigDecimal amount;
  @NotNull
  private Boolean state;
}
