package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.CorporateCredit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CorporateCreditRepository extends ReactiveMongoRepository<CorporateCredit, String> {
}
