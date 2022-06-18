package com.nttdata.bootcamp.registerproduct.service.external;


import com.nttdata.bootcamp.registerproduct.entity.TypeAccount;
import com.nttdata.bootcamp.registerproduct.entity.TypeCredit;
import com.nttdata.bootcamp.registerproduct.entity.TypeCreditCard;
import reactor.core.publisher.Mono;

public interface IExternalProductService {
    Mono<TypeAccount> findTypeAccountById(String id);

    Mono<TypeCredit> findTypeCreditById(String id);

    Mono<TypeCreditCard> findTypeCreditCardById(String id);
}
