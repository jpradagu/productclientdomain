package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.Credit;
import com.nttdata.bootcamp.registerproduct.model.CustomerType;
import com.nttdata.bootcamp.registerproduct.repository.CreditRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * service  credit.
 */
@Service
@Slf4j
public class CreditService {

  @Autowired
  private CreditRepository corporateCreditRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;

  /**
   * findAll credit.
   */
  public Flux<Credit> findAll() {
    log.info("CorporateCreditService findAll ->");
    return corporateCreditRepository.findAll();
  }

  /**
   * find credit.
   */
  public Mono<Credit> findById(String id) {
    log.info("CorporateCreditService findById ->");
    return corporateCreditRepository.findById(id);
  }

  /**
   * create credit.
   */
  public Mono<Credit> create(Credit credit) {
    log.info("CorporateAccountService create ->");
    if (credit.getCustomerType().equals(CustomerType.PERSONAL)) {
      return externalAccountService.findCustomerPersonalById(credit.getCustomerId())
          .flatMap(
              customer -> externalProductService.findTypeCreditById(credit.getTypeCreditId())
                  .flatMap(typeCredit -> {
                    if (typeCredit.getAllowCompany().equals(true)) {
                      return corporateCreditRepository.save(credit);
                    } else {
                      return Mono.error(
                          new RuntimeException(
                              "Credit account not allowed for commercial account"));
                    }
                  }));
    } else {
      return externalAccountService.findCustomerCommercialById(credit.getCustomerId())
          .flatMap(
              customer -> externalProductService.findTypeCreditById(credit.getTypeCreditId())
                  .flatMap(typeCredit -> {
                    if (typeCredit.getAllowCompany().equals(true)) {
                      return corporateCreditRepository.save(credit);
                    } else {
                      return Mono.error(
                          new RuntimeException(
                              "Credit account not allowed for commercial account"));
                    }
                  }));
    }

  }

  /**
   * update credit.
   */
  public Mono<Credit> update(Credit credit, String id) {
    log.info("CorporateAccountService update ->");
    return corporateCreditRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("Corporate account not found")))
        .flatMap(p -> {
          credit.setId(id);
          return corporateCreditRepository.save(credit);
        });
  }

  /**
   * delete credit.
   */
  public Mono<Void> delete(Credit corporateCredit) {
    log.info("CorporateAccountService delete ->");
    return corporateCreditRepository.delete(corporateCredit);
  }

}
