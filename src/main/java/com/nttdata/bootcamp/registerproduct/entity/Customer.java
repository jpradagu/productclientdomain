package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Customer {
    @NotNull
    private String id;
    private String documentType;
    private String numberDocument;
    private String name;
    private String email;
    private String phone;

}
