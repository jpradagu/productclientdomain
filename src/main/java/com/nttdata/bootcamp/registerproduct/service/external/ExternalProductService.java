package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeAccount;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeCredit;
import com.nttdata.bootcamp.registerproduct.service.external.dto.TypeCreditCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 * external product service.
 */
@Service
@Slf4j
public class ExternalProductService implements IExternalProductService {

  private static final String URL_GATEWAY = "http://localhost:8080/api/product";

  @Autowired
  private WebClient.Builder webClient;

  /**
   * find typeAccount by identifier.
   */
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

  /**
   * find typeCredit by identifier.
   */
  @Override
  public Mono<TypeCredit> findTypeCreditById(String id) {
    return webClient.baseUrl(URL_GATEWAY).build().get().uri("/credit-account/".concat(id))
        .retrieve()
        .bodyToMono(TypeCredit.class).onErrorResume(error -> {
          log.debug(error.getMessage());
          WebClientResponseException resp = (WebClientResponseException) error;
          if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return Mono.error(new RuntimeException("Type credit not found"));
          }
          return Mono.error(error);
        });
  }

  /**
   * find typeCreditCard by identifier.
   */
  @Override
  public Mono<TypeCreditCard> findTypeCreditCardById(String id) {
    return webClient.baseUrl(URL_GATEWAY).build().get().uri("/credit-account/".concat(id))
        .retrieve()
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
