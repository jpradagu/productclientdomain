package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.PersonalCreditCard;
import com.nttdata.bootcamp.registerproduct.repository.PersonalCreditCardRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalCreditCardService {
    private final Logger log = LoggerFactory.getLogger(PersonalCreditCardService.class);
    @Autowired
    private PersonalCreditCardRepository personalCreditCardRepository;
    @Autowired
    private IExternalAccountService externalAccountService;
    @Autowired
    private IExternalProductService externalProductService;

    public Flux<PersonalCreditCard> findAll() {
        log.info("PersonalCreditCardService findAll ->");
        return personalCreditCardRepository.findAll();
    }

    public Mono<PersonalCreditCard> findById(String id) {
        log.info("PersonalCreditCardService findById ->");
        return personalCreditCardRepository.findById(id);
    }

    public Mono<PersonalCreditCard> create(PersonalCreditCard personalCredit) {
        log.info("PersonalCreditCardService create ->");
        return externalAccountService
                .findCustomerPersonalById(personalCredit.getCustomer().getId())
                .flatMap(customer -> externalProductService
                        .findTypeCreditCardById(personalCredit.getTypeCreditCard().getId())
                        .flatMap(typeCreditCard ->  {
                                    if (typeCreditCard.getAllowPerson()) {
                                        personalCredit.setCustomer(customer);
                                        personalCredit.setTypeCreditCard(typeCreditCard);
                                        return personalCreditCardRepository.save(personalCredit);
                                    } else {
                                        return Mono.error(new RuntimeException("It is not allowed to create a credit card for a personal customer"));
                                    }
                                }));
    }

    public Mono<Void> delete(PersonalCreditCard customerCredit) {
        log.info("PersonalCreditCardService delete ->");
        return personalCreditCardRepository.delete(customerCredit);
    }
}
