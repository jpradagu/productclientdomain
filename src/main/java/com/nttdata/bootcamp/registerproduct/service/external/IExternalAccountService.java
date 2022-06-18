package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.entity.Customer;
import reactor.core.publisher.Mono;

public interface IExternalAccountService {
    Mono<Customer> findCustomerPersonalById(String id);
    Mono<Customer> findCustomerCommercialById(String id);
}
