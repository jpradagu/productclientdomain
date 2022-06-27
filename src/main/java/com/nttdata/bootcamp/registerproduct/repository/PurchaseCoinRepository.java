package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.model.PurchaseCoin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * PurchaseCoinRepository.
 */
public interface PurchaseCoinRepository extends ReactiveMongoRepository<PurchaseCoin, String> {
}
