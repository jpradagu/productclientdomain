package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CreditCard;
import com.nttdata.bootcamp.registerproduct.repository.CreditCardRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CorporateCreditCard service.
 */
@Service
@Slf4j
public class CreditCardService {


  @Autowired
  private CreditCardRepository creditCardRepository;

  @Autowired
  private IExternalAccountService externalAccountService;

  @Autowired
  private IExternalProductService externalProductService;

  /**
   * FindAll corporate credit card.
   */
  public Flux<CreditCard> findAll() {
    log.info("CorporateCreditCardService findAll ->");
    return creditCardRepository.findAll();
  }

  /**
   * Find corporate credit card.
   */
  public Mono<CreditCard> findById(String id) {
    log.info("CorporateCreditCardService findById ->");
    return creditCardRepository.findById(id);
  }

  /**
   * Create corporate credit card.
   */
  public Mono<CreditCard> create(CreditCard corporateCreditCard) {
    log.info("CorporateCreditCardService create ->");
    return externalAccountService
        .findCustomerCommercialById(corporateCreditCard.getCustomerId())
        .flatMap(customer -> externalProductService
            .findTypeCreditCardById(corporateCreditCard.getTypeCreditCardId())
            .flatMap(typeCredit -> {
              if (typeCredit.getAllowCompany()) {
                return creditCardRepository.save(corporateCreditCard);
              } else {
                return Mono.error(
                    new RuntimeException("Credit card account not allowed for commercial account"));
              }
            })
        );
  }

  /**
   * Delete corporate credit card.
   */
  public Mono<Void> delete(CreditCard corporateCreditCard) {
    log.info("CorporateCreditCardService delete ->");
    return creditCardRepository.delete(corporateCreditCard);
  }

}
