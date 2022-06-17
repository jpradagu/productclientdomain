package com.nttdata.bootcamp.productclientdomain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {
    @Id
    private String idCustomer;
    private String documentType;
    private String numberDocument;
    private String name;
    private String email;
    private String phone;

}
