package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** PurchaseCoin.*/
@Data
@Document(collection = "purchaseCoins")
public class PurchaseCoin {
  @Id
  private String id;
  @NotNull
  private String tariffId;
  @NotNull
  private BigDecimal amount;
  @NotNull
  private MethodPayment methodPayment;
  @NotNull
  private String receivingNumberId;
  @NotNull
  private String customerWalletId;
  private String numberTransaction;
  private BigDecimal requestedAmount;
  private BigDecimal purchaseAmount;
  private BigDecimal saleAmount;
}
