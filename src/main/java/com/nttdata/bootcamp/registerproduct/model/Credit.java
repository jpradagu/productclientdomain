package com.nttdata.bootcamp.registerproduct.model;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "credits")
public class Credit {
  @Id
  private String id;
  @NotNull
  private String code;
  @NotNull
  private BigDecimal amountGiven;
  @NotNull
  private BigDecimal amountPaid;
  @NotNull
  private Integer fees;
  @NotNull
  private Integer feesPaid;
  @NotNull
  private CustomerType customerType;
  @NotNull
  private String customerId;
  @NotNull
  private String typeCreditId;
  @NotNull
  private Boolean state;
}
