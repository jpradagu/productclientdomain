package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.CorporateCreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CorporateCreditCardRepository extends ReactiveMongoRepository<CorporateCreditCard, String> {
}
