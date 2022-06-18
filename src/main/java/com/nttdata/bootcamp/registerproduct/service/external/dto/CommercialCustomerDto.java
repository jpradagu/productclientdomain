package com.nttdata.bootcamp.registerproduct.service.external.dto;

import lombok.Data;

@Data
public class CommercialCustomerDto {
    private String id;
    private String ruc;
    private String reasonSocial;
    private String email;
    private String phone;
}
