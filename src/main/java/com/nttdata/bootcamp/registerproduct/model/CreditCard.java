package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * CreditCard.
 */
@Data
@Document(collection = "creditCards")
public class CreditCard {
  @Id
  private String id;
  @NotNull
  private String creditCardNumber;
  @NotNull
  private BigDecimal limitAmount;
  @NotNull
  private BigDecimal usedAmount;
  @NotNull
  private Date openingDate;
  @NotNull
  private CustomerType customerType;
  @NotNull
  private String customerId;
  @NotNull
  private String typeCreditCardId;
  @NotNull
  private Boolean state;
}
