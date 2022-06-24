package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * CustomerCreditCard.
 */
@Data
@Document(collection = "customerDebitCards")
public class CustomerDebitCard {
  @Id
  private String id;
  @NotNull
  private String debitCardNumber;
  @NotNull
  private String accountId;
  @NotNull
  private String customerWalletId;
  @NotNull
  private BigDecimal amount;
}
