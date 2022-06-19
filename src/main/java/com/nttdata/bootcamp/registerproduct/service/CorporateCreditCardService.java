package com.nttdata.bootcamp.registerproduct.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.registerproduct.model.CorporateCreditCard;
import com.nttdata.bootcamp.registerproduct.repository.CorporateCreditCardRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateCreditCardService {
	private final Logger log = LoggerFactory.getLogger(CorporateCreditService.class);

	@Autowired
	private CorporateCreditCardRepository corporateCreditCardRepository;

	@Autowired
	private IExternalAccountService externalAccountService;

	@Autowired
	private IExternalProductService externalProductService;

	public Flux<CorporateCreditCard> findAll() {
		log.info("CorporateCreditCardService findAll ->");
		return corporateCreditCardRepository.findAll();
	}

	public Mono<CorporateCreditCard> findById(String id) {
		log.info("CorporateCreditCardService findById ->");
		return corporateCreditCardRepository.findById(id);
	}
	
    public Mono<CorporateCreditCard> create(CorporateCreditCard corporateCreditCard) {
        log.info("CorporateCreditCardService create ->");
        return externalAccountService
                .findCustomerCommercialById(corporateCreditCard.getCustomer().getId())
                .flatMap(customer -> externalProductService
                        .findTypeCreditCardById(corporateCreditCard.getTypeCreditCard().getId())
                        .flatMap(typeCredit -> {
                            if (typeCredit.getAllowCompany()) {
                                corporateCreditCard.setTypeCreditCard(typeCredit);
                                corporateCreditCard.setCustomer(customer);
                                return corporateCreditCardRepository.save(corporateCreditCard);
                            } else {
                                return Mono.error(new RuntimeException("Credit card account not allowed for commercial account"));
                            }
                        })
                );
    }

    public Mono<Void> delete(CorporateCreditCard corporateCreditCard) {
        log.info("CorporateCreditCardService delete ->");
        return corporateCreditCardRepository.delete(corporateCreditCard);
    }

}
