package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.PersonalAccount;
import com.nttdata.bootcamp.registerproduct.repository.PersonalAccountRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service PersonalAccount.
 */
@Service
@Slf4j
public class PersonalAccountService {

  @Autowired
  private PersonalAccountRepository personalAccountRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;

  /**
   * findAll PersonalAccountResponse.
   */
  public Flux<PersonalAccount> findAll() {
    log.info("PersonalAccountService findAll ->");
    return personalAccountRepository.findAll();
  }

  /**
   * find PersonalAccount.
   */
  public Mono<PersonalAccount> findById(String id) {
    log.info("PersonalAccountService findById ->");
    return personalAccountRepository.findById(id);
  }

  /**
   * create personal account.
   */
  public Mono<PersonalAccount> create(PersonalAccount personalAccount) {
    log.info("PersonalAccountService create ->");
    return externalAccountService
        .findCustomerPersonalById(personalAccount.getCustomerId())
        .flatMap(customer -> externalProductService
            .findTypeAccountById(personalAccount.getTypeAccountId())
            .flatMap(typeAccount -> personalAccountRepository
                .findByCustomerId(customer.getId())
                .collectList().flatMap(lst -> {
                  if (typeAccount.getAllowPerson().equals(true)) {
                    if (!lst.isEmpty()) {
                      return Mono.error(
                          new RuntimeException("Only one bank account per person is allowed"));
                    }
                    if (personalAccount.getAmount()
                        .compareTo(typeAccount.getMinimumOpeningAmount()) < 0) {
                      return Mono.error(
                          new RuntimeException("The personal account requires a minimum amount"));
                    }
                    return personalAccountRepository.save(personalAccount);
                  } else {
                    return Mono.error(
                        new RuntimeException("Bank account not allowed for personal customer"));
                  }
                })));
  }

  /**
   * Update corporate account.
   */
  public Mono<PersonalAccount> update(PersonalAccount personalAccount, String id) {
    log.info("CorporateAccountService update ->");
    return personalAccountRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("personal account not found")))
        .flatMap(p -> {
          personalAccount.setId(id);
          return personalAccountRepository.save(personalAccount);
        });
  }

  /**
   * delete personal account.
   */
  public Mono<Void> delete(PersonalAccount personalAccount) {
    log.info("AccountBankService delete ->");
    return personalAccountRepository.delete(personalAccount);
  }


}
