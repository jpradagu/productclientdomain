package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.PersonalAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PersonalAccountRepository extends ReactiveMongoRepository<PersonalAccount, String> {
    Flux<PersonalAccount> findByTypeAccountTypeAndCustomerId(String type, String id);
}
