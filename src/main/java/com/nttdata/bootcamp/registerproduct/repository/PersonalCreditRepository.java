package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.PersonalCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PersonalCreditRepository extends ReactiveMongoRepository<PersonalCredit, String> {
    Flux<PersonalCredit> findByCustomerId(String id);
}
