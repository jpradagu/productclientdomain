package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CustomerDebitCard;
import com.nttdata.bootcamp.registerproduct.repository.CustomerDebitCardRepository;
import com.nttdata.bootcamp.registerproduct.repository.PersonalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerDebitCardService.
 */
@Service
public class CustomerDebitCardService {
  @Autowired
  private CustomerDebitCardRepository customerDebitCardRepository;
  @Autowired
  private PersonalAccountRepository personalAccountRepository;


  /**
   * findAll CustomerDebitCard.
   */
  public Flux<CustomerDebitCard> findAll() {
    return customerDebitCardRepository.findAll();
  }

  /**
   * findById CustomerDebitCard.
   */
  @Cacheable(value = "customeDebitCardFindCache")
  public Mono<CustomerDebitCard> findById(String id) {
    return customerDebitCardRepository.findById(id);
  }

  /**
   * create CustomerDebitCard.
   */
  public Mono<CustomerDebitCard> create(CustomerDebitCard customerDebitCard) {
    return personalAccountRepository.findById(customerDebitCard.getAccountId())
        .switchIfEmpty(Mono.error(new RuntimeException("Account bank not found")))
        .flatMap(personalAccount -> customerDebitCardRepository.save(customerDebitCard));
  }

  /**
   * update CustomerDebitCard.
   */
  public Mono<CustomerDebitCard> update(CustomerDebitCard customerDebitCard, String id) {
    return customerDebitCardRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("Customer debit card not found")))
        .flatMap(p -> customerDebitCardRepository.save(customerDebitCard));
  }

  /**
   * delete CustomerDebitCard.
   */
  public Mono<Void> delete(CustomerDebitCard customerDebitCard) {
    return customerDebitCardRepository.delete(customerDebitCard);
  }

  /**
   * findByCustomerWalletId CustomerDebitCard.
   */
  public Mono<CustomerDebitCard> findByCustomerWalletId(String customerWalletId) {
    return customerDebitCardRepository.findByCustomerWalletId(customerWalletId);
  }
}
