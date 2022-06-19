package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.PersonalCredit;

import reactor.core.publisher.Flux;

public interface PersonalCreditRepository extends ReactiveMongoRepository<PersonalCredit, String> {
    Flux<PersonalCredit> findByCustomerId(String id);
}
