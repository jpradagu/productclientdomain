package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    private LocalDateTime openingDate;
    @NotNull
    private Customer customer;
    @NotNull
    private TypeAccount typeAccount;
    @NotNull
    private Boolean state;
    @NotEmpty
    private List<Customer> holders;
    private List<Customer> signers;
}
