package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.service.external.dto.CommercialCustomer;
import com.nttdata.bootcamp.registerproduct.service.external.dto.PersonalCustomer;
import reactor.core.publisher.Mono;

/**
 * Interface external account service.
 */
public interface IExternalAccountService {

  /** find customer personal by id.*/
  Mono<PersonalCustomer> findCustomerPersonalById(String id);

  /** find commercial customer by id.*/
  Mono<CommercialCustomer> findCustomerCommercialById(String id);
  
}
