package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.CorporateCreditCard;

public interface CorporateCreditCardRepository extends ReactiveMongoRepository<CorporateCreditCard, String> {
}
