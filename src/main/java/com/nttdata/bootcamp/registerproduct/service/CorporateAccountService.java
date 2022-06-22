package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CorporateAccount;
import com.nttdata.bootcamp.registerproduct.repository.CorporateAccountRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CorporateAccount service.
 */
@Service
@Slf4j
public class CorporateAccountService {

  @Autowired
  private CorporateAccountRepository corporateAccountRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;


  /**
   * findAll Corporate account response.
   */
  public Flux<CorporateAccount> findAll() {
    log.info("CorporateAccountService findAll ->");
    return corporateAccountRepository.findAll();
  }


  /**
   * find CorporateAccount by id.
   */
  public Mono<CorporateAccount> findById(String id) {
    log.info("CorporateAccountService findById ->");
    return corporateAccountRepository.findById(id);
  }

  /**
   * Create corporate account.
   */
  public Mono<CorporateAccount> create(CorporateAccount corporateAccount) {
    log.info("CorporateAccountService create ->");
    return externalAccountService
        .findCustomerCommercialById(corporateAccount.getCustomerId())
        .flatMap(customer -> externalProductService
            .findTypeAccountById(corporateAccount.getTypeAccountId())
            .flatMap(typeAccount -> {
              if (typeAccount.getAllowCompany().equals(true)) {
                if (corporateAccount.getAmount()
                    .compareTo(typeAccount.getMinimumOpeningAmount()) < 0) {
                  return Mono.error(
                      new RuntimeException("The corporate account requires a minimum amount"));
                }
                return corporateAccountRepository.save(corporateAccount);
              } else {
                return Mono.error(
                    new RuntimeException("Bank account not allowed for commercial account"));
              }
            })
        );
  }

  /**
   * Update corporate account.
   */
  public Mono<CorporateAccount> update(CorporateAccount corporateAccount, String id) {
    log.info("CorporateAccountService update ->");
    return corporateAccountRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("Corporate account not found")))
        .flatMap(p -> {
          corporateAccount.setId(id);
          return corporateAccountRepository.save(corporateAccount);
        });
  }

  /**
   * delete corporateAccount.
   */
  public Mono<Void> delete(CorporateAccount corporateAccount) {
    log.info("CorporateAccountService delete ->");
    return corporateAccountRepository.delete(corporateAccount);
  }

}
