package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.PersonalCreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonalCreditCardRepository extends ReactiveMongoRepository<PersonalCreditCard, String> {
}
