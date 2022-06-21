package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.model.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard, String> {
}
