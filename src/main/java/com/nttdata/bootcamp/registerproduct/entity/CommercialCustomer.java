package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;

@Data
public class CommercialCustomer {
    private String id;
    private String ruc;
    private String reasonSocial;
    private String email;
    private String phone;
}
