package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeCredit;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeCreditCard;
import reactor.core.publisher.Mono;

/**
 * interface external product service.
 */
public interface IExternalProductService {

  Mono<TypeAccount> findTypeAccountById(String id);

  Mono<TypeCredit> findTypeCreditById(String id);

  Mono<TypeCreditCard> findTypeCreditCardById(String id);
}
