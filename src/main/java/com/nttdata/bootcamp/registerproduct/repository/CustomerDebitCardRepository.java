package com.nttdata.bootcamp.registerproduct.repository;

import com.nttdata.bootcamp.registerproduct.model.CustomerDebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * CustomerDebitCardRepository.
 */
public interface CustomerDebitCardRepository
    extends ReactiveMongoRepository<CustomerDebitCard, String> {
  Mono<CustomerDebitCard> findByCustomerWalletId(String customerWalletId);
}
