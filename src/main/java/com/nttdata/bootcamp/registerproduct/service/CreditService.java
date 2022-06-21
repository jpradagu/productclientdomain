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
 * service corporate credit.
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
   * findAll corporate credit.
   */
  public Flux<Credit> findAll() {
    log.info("CorporateCreditService findAll ->");
    return corporateCreditRepository.findAll();
  }

  /**
   * find corporate credit.
   */
  public Mono<Credit> findById(String id) {
    log.info("CorporateCreditService findById ->");
    return corporateCreditRepository.findById(id);
  }

  /**
   * create corporate credit.
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
   * delete corporate credit.
   */
  public Mono<Void> delete(Credit corporateCredit) {
    log.info("CorporateAccountService delete ->");
    return corporateCreditRepository.delete(corporateCredit);
  }

}
