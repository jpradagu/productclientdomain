package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.model.CommercialCustomer;
import com.nttdata.bootcamp.registerproduct.model.PersonalCustomer;

import reactor.core.publisher.Mono;

public interface IExternalAccountService {
	Mono<PersonalCustomer> findCustomerPersonalById(String id);

	Mono<CommercialCustomer> findCustomerCommercialById(String id);
}
