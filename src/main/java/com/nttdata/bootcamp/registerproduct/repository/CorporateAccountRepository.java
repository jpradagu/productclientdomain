package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.CorporateAccount;

public interface CorporateAccountRepository extends ReactiveMongoRepository<CorporateAccount,String> {
}
