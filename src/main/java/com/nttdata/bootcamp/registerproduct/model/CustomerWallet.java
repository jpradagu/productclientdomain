package com.nttdata.bootcamp.registerproduct.model;

import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * CustomerWallet.
 */
@Document(collection = "customerWallets")
@Data
public class CustomerWallet {
  @Id
  private String id;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date openingDate;
  @NotNull
  private String imei;
  @NotNull
  private DocumentType documentType;
  @NotNull
  private String documentNumber;
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private String email;
}
