package com.nttdata.bootcamp.registerproduct.service.external.dto;

import lombok.Data;

/**
 * Personal Customer model.
 */
@Data
public class PersonalCustomer {
  private String id;
  private String dni;
  private String name;
  private String lastname;
  private String email;
  private String phone;
}
