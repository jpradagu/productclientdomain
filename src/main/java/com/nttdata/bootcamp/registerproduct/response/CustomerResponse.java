package com.nttdata.bootcamp.registerproduct.response;

import lombok.Data;

@Data
public class CustomerResponse {
  private String id;
  private String type;
  private String documentNumber;
  private String name;
  private String email;
  private String phone;
}
