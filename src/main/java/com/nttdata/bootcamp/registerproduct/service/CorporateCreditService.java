package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.CorporateCredit;
import com.nttdata.bootcamp.registerproduct.repository.CorporateCreditRepository;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalAccountService;
import com.nttdata.bootcamp.registerproduct.service.external.IExternalProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CorporateCreditService {
	private final Logger log = LoggerFactory.getLogger(CorporateCreditService.class);

	@Autowired
	private CorporateCreditRepository corporateCreditRepository;

	@Autowired
	private IExternalAccountService externalAccountService;

	@Autowired
	private IExternalProductService externalProductService;

	public Flux<CorporateCredit> findAll() {
		log.info("CorporateCreditService findAll ->");
		return corporateCreditRepository.findAll();
	}

	public Mono<CorporateCredit> findById(String id) {
		log.info("CorporateCreditService findById ->");
		return corporateCreditRepository.findById(id);
	}

	public Mono<CorporateCredit> create(CorporateCredit corporateCredit) {
		log.info("CorporateAccountService create ->");
		return externalAccountService.findCustomerCommercialById(corporateCredit.getCustomer().getId())
				.flatMap(customer -> externalProductService.findTypeCreditById(corporateCredit.getTypeCredit().getId())
						.flatMap(typeCredit -> {
							if (typeCredit.getAllowCompany()) {
								corporateCredit.setTypeCredit(typeCredit);
								corporateCredit.setCustomer(customer);
								return corporateCreditRepository.save(corporateCredit);
							} else {
								return Mono.error(
										new RuntimeException("Credit account not allowed for commercial account"));
							}
						}));
	}

	public Mono<Void> delete(CorporateCredit corporateCredit) {
		log.info("CorporateAccountService delete ->");
		return corporateCreditRepository.delete(corporateCredit);
	}
}
