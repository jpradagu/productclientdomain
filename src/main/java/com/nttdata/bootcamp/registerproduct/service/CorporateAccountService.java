package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CorporateAccount;
import com.nttdata.bootcamp.registerproduct.repository.CorporateAccountRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateAccountService {

    private final Logger log = LoggerFactory.getLogger(CorporateAccountService.class);

    @Autowired
    private CorporateAccountRepository corporateAccountRepository;

    @Autowired
    private IExternalAccountService externalAccountService;

    @Autowired
    private IExternalProductService externalProductService;

    public Flux<CorporateAccount> findAll() {
        log.info("CorporateAccountService findAll ->");
        return corporateAccountRepository.findAll();
    }

    public Mono<CorporateAccount> findById(String id) {
        log.info("CorporateAccountService findById ->");
        return corporateAccountRepository.findById(id);
    }

    public Mono<CorporateAccount> create(CorporateAccount corporateAccount) {
        log.info("CorporateAccountService create ->");
        return externalAccountService
                .findCustomerCommercialById(corporateAccount.getCustomer().getId())
                .flatMap(customer -> externalProductService
                        .findTypeAccountById(corporateAccount.getTypeAccount().getId())
                        .flatMap(typeAccount -> {
                            if (typeAccount.getAllowCompany()) {
                                corporateAccount.setTypeAccount(typeAccount);
                                corporateAccount.setCustomer(customer);
                                return corporateAccountRepository.save(corporateAccount);
                            } else {
                                return Mono.error(new RuntimeException("Bank account not allowed for commercial account"));
                            }
                        })
                );
    }

    public Mono<Void> delete(CorporateAccount corporateAccount) {
        log.info("CorporateAccountService delete ->");
        return corporateAccountRepository.delete(corporateAccount);
    }

}
