package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "creditCards")
public class CreditCard {
  @Id
  private String id;
  @NotNull
  private String code;
  @NotNull
  private String creditCardNumber;
  @NotNull
  private BigDecimal limitAmount;
  @NotNull
  private BigDecimal usedAmount;
  @NotNull
  private Integer fees;
  @NotNull
  private Integer feesPaid;
  @NotNull
  private Integer cutoffDate;
  @NotNull
  private Integer limitDate;
  @NotNull
  private Date openingDate;
  @NotNull
  private Date deliveryDate;
  @NotNull
  private CustomerType customerType;
  @NotNull
  private String customerId;
  @NotNull
  private String creditCardId;
  @NotNull
  private Boolean state;
}
