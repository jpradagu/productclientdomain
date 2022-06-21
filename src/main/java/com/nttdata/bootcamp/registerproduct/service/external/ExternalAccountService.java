package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.service.external.dto.CommercialCustomer;
import com.nttdata.bootcamp.registerproduct.service.external.dto.PersonalCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/** External Account service.*/
@Service
public class ExternalAccountService implements IExternalAccountService {

  @Autowired
  private WebClient.Builder webClient;

  private static final String URL_GATEWAY = "http://localhost:8080/api/customer";

  /** find customer personal by identifier.*/
  @Override
  public Mono<PersonalCustomer> findCustomerPersonalById(String id) {
    return webClient.baseUrl(URL_GATEWAY).build().get().uri("/personal/".concat(id)).retrieve()
        .bodyToMono(PersonalCustomer.class).onErrorResume(error -> {
          WebClientResponseException resp = (WebClientResponseException) error;
          if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return Mono.error(new RuntimeException("Personal customer not found"));
          }
          return Mono.error(error);
        });
  }

  /** find commercial customer by identifier.*/
  @Override
  public Mono<CommercialCustomer> findCustomerCommercialById(String id) {
    return webClient.baseUrl(URL_GATEWAY).build().get().uri("/enterprise/".concat(id)).retrieve()
        .bodyToMono(CommercialCustomer.class).onErrorResume(error -> {
          WebClientResponseException resp = (WebClientResponseException) error;
          if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
            return Mono.error(new RuntimeException("Enterprise customer not found"));
          }
          return Mono.error(error);
        });
  }
}
