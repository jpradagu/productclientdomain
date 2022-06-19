package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.entity.PersonalAccount;
import com.nttdata.bootcamp.registerproduct.repository.PersonalAccountRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonalAccountService {

    private final Logger log = LoggerFactory.getLogger(PersonalAccountService.class);
    @Autowired
    private PersonalAccountRepository personalAccountRepository;

    @Autowired
    private IExternalAccountService externalAccountService;

    @Autowired
    private IExternalProductService externalProductService;

    public Flux<PersonalAccount> findAll() {
        log.info("PersonalAccountService findAll ->");
        return personalAccountRepository.findAll();
    }

    public Mono<PersonalAccount> findById(String id) {
        log.info("PersonalAccountService findById ->");
        return personalAccountRepository.findById(id);
    }

    public Mono<PersonalAccount> create(PersonalAccount personalAccount) {
        log.info("PersonalAccountService create ->");
        return externalAccountService
                .findCustomerPersonalById(personalAccount.getCustomer().getId())
                .flatMap(customer -> externalProductService
                        .findTypeAccountById(personalAccount.getTypeAccount().getId())
                        .flatMap(typeAccount -> personalAccountRepository
                                .findByTypeAccountTypeAndCustomerId(typeAccount.getType(), customer.getId())
                                .collectList().flatMap(lst -> {
                                    if (typeAccount.getAllowPerson()) {
                                        if (lst.size() > 0) {
                                            return Mono.error(new RuntimeException("Only one bank account per person is allowed"));
                                        }
                                        personalAccount.setTypeAccount(typeAccount);
                                        personalAccount.setCustomer(customer);
                                        return personalAccountRepository.save(personalAccount);
                                    } else {
                                        return Mono.error(new RuntimeException("Bank account not allowed for personal customer"));
                                    }
                                })));
    }

    public Mono<Void> delete(PersonalAccount personalAccount) {
        log.info("AccountBankService delete ->");
        return personalAccountRepository.delete(personalAccount);
    }
}
