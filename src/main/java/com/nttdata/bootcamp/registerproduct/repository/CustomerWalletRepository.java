package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.model.CustomerWallet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * CustomerWalletRepository.
 */
public interface CustomerWalletRepository extends ReactiveMongoRepository<CustomerWallet, String> {
  Mono<CustomerWallet> findByPhone(String phone);
}
