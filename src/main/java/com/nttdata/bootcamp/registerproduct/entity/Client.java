package com.nttdata.bootcamp.registerproduct.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
