package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "corporateAccounts")
public class CorporateAccount {
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
    private CommercialCustomer customer;
    @NotNull
    private TypeAccount typeAccount;
    @NotNull
    private Boolean state;
    @NotEmpty
    private List<Client> holders;
    private List<Client> signers;
}
