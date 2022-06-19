package com.nttdata.bootcamp.registerproduct.model;

import lombok.Data;

@Data
public class CommercialCustomer {
    private String id;
    private String ruc;
    private String reasonSocial;
    private String email;
    private String phone;
}
