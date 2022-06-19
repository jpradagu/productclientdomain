package com.nttdata.bootcamp.registerproduct.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.bootcamp.registerproduct.model.CorporateCredit;

public interface CorporateCreditRepository extends ReactiveMongoRepository<CorporateCredit, String> {
}
