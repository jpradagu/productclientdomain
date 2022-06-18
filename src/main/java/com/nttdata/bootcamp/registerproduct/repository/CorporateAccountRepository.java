package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.entity.CorporateAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CorporateAccountRepository extends ReactiveMongoRepository<CorporateAccount,String> {
}
