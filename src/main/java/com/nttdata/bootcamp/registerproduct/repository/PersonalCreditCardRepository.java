package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.PersonalCreditCard;

public interface PersonalCreditCardRepository extends ReactiveMongoRepository<PersonalCreditCard, String> {
}
