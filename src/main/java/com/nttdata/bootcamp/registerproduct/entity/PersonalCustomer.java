package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;

@Data
public class PersonalCustomer {
    private String id;
    private String dni;
    private String name;
    private String lastname;
    private String email;
    private String phone;
}