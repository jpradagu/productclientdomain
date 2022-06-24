package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * PaymentWallet.
 */
@Data
public class PaymentWallet {
  @NotNull
  private String customerWalletId;
  @NotNull
  private String phone;
  private String receiverWalletId;
  @NotNull
  private BigDecimal amount;
}
