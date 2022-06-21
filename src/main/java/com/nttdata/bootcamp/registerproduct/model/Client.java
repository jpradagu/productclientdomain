package com.nttdata.bootcamp.registerproduct.model;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Client {
  @NotNull
  private String dni;
  @NotNull
  private String name;
  private String email;
  @NotNull
  private String phone;
}
