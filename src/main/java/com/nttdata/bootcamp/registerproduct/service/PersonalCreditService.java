package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.entity.PersonalCredit;
import com.nttdata.bootcamp.registerproduct.repository.PersonalCreditRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalCreditService {

    private final Logger log = LoggerFactory.getLogger(PersonalCreditService.class);
    @Autowired
    private PersonalCreditRepository personalCreditRepository;
    @Autowired
    private IExternalAccountService externalAccountService;
    @Autowired
    private IExternalProductService externalProductService;

    public Flux<PersonalCredit> findAll() {
        log.info("CustomerCreditService findAll ->");
        return personalCreditRepository.findAll();
    }

    public Mono<PersonalCredit> findById(String id) {
        log.info("CustomerCreditService findById ->");
        return personalCreditRepository.findById(id);
    }

    public Mono<PersonalCredit> create(PersonalCredit personalCredit) {
        log.info("CustomerCreditService create ->");
        return externalAccountService
                .findCustomerPersonalById(personalCredit.getCustomer().getId())
                .flatMap(customer -> externalProductService
                        .findTypeCreditById(personalCredit.getTypeCredit().getId())
                        .flatMap(typeCredit -> personalCreditRepository
                                .findByCustomerId(customer.getId())
                                .collectList().flatMap(lst -> {
                                    if (typeCredit.getAllowPerson()) {
                                        if (lst.size() > 0) {
                                            return Mono.error(new RuntimeException("Only one credit per person is allowed"));
                                        }
                                        personalCredit.setCustomer(customer);
                                        personalCredit.setTypeCredit(typeCredit);
                                        return personalCreditRepository.save(personalCredit);
                                    } else {
                                        return Mono.error(new RuntimeException("It is not allowed to create a credit account for a personal customer"));
                                    }
                                })));
    }

    public Mono<Void> delete(PersonalCredit customerCredit) {
        log.info("CustomerCreditService delete ->");
        return personalCreditRepository.delete(customerCredit);
    }
}
