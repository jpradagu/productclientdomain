package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.PersonalAccount;

import reactor.core.publisher.Flux;

public interface PersonalAccountRepository extends ReactiveMongoRepository<PersonalAccount, String> {
    Flux<PersonalAccount> findByTypeAccountTypeAndCustomerId(String type, String id);
}
