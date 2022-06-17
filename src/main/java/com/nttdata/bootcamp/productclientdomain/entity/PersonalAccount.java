package com.nttdata.bootcamp.productclientdomain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    private LocalDateTime openingDate;
    @NotNull
    private Customer customer;
    @NotNull
    private TypeAccountBank typeAccountBank;
    @NotNull
    private Boolean state;
}
