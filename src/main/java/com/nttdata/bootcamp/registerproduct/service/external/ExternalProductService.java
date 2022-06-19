package com.nttdata.bootcamp.registerproduct.service.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.nttdata.bootcamp.registerproduct.model.TypeAccount;
import com.nttdata.bootcamp.registerproduct.model.TypeCredit;
import com.nttdata.bootcamp.registerproduct.model.TypeCreditCard;

import reactor.core.publisher.Mono;

@Service
public class ExternalProductService implements IExternalProductService {
	private final Logger log = LoggerFactory.getLogger(ExternalProductService.class);
	@Autowired
	private WebClient.Builder webClient;

	private static final String URL_GATEWAY = "http://localhost:8080/api/product";

	@Override
	public Mono<TypeAccount> findTypeAccountById(String id) {
		return webClient.baseUrl(URL_GATEWAY).build().get().uri("/account-bank/".concat(id)).retrieve()
				.bodyToMono(TypeAccount.class).onErrorResume(error -> {
					log.debug(error.getMessage());
					WebClientResponseException resp = (WebClientResponseException) error;
					if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
						return Mono.error(new RuntimeException("Type account not found"));
					}
					return Mono.error(error);
				});
	}

	@Override
	public Mono<TypeCredit> findTypeCreditById(String id) {
		return webClient.baseUrl(URL_GATEWAY).build().get().uri("/credit-account/".concat(id)).retrieve()
				.bodyToMono(TypeCredit.class).onErrorResume(error -> {
					log.debug(error.getMessage());
					WebClientResponseException resp = (WebClientResponseException) error;
					if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
						return Mono.error(new RuntimeException("Type credit not found"));
					}
					return Mono.error(error);
				});
	}

	@Override
	public Mono<TypeCreditCard> findTypeCreditCardById(String id) {
		return webClient.baseUrl(URL_GATEWAY).build().get().uri("/credit-account/".concat(id)).retrieve()
				.bodyToMono(TypeCreditCard.class).onErrorResume(error -> {
					log.debug(error.getMessage());
					WebClientResponseException resp = (WebClientResponseException) error;
					if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
						return Mono.error(new RuntimeException("Type credit card not found"));
					}
					return Mono.error(error);
				});
	}
}
