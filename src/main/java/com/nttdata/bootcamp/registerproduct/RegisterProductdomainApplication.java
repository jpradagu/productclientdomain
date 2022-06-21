package com.nttdata.bootcamp.registerproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Register product Domain application.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RegisterProductdomainApplication {

  public static void main(String[] args) {
    SpringApplication.run(RegisterProductdomainApplication.class, args);
  }

}
