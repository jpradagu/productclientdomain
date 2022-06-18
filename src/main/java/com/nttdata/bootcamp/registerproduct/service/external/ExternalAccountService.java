package com.nttdata.bootcamp.registerproduct.service.external;

import com.nttdata.bootcamp.registerproduct.entity.Customer;
import com.nttdata.bootcamp.registerproduct.service.external.dto.CommercialCustomerDto;
import com.nttdata.bootcamp.registerproduct.service.external.dto.PersonalCustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ExternalAccountService implements IExternalAccountService {

    @Autowired
    private WebClient.Builder webClient;

    private static final String URL_GATEWAY = "http://localhost:8080";

    @Override
    public Mono<Customer> findCustomerPersonalById(String id) {
        return webClient
                .baseUrl(URL_GATEWAY).build().get().uri("/api/customer/personal/".concat(id))
                .retrieve()
                .bodyToMono(PersonalCustomerDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException resp = (WebClientResponseException) error;
                    if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("Personal customer not found"));
                    }
                    return Mono.error(error);
                }).flatMap(p -> {
                    Customer customer = new Customer();
                    customer.setDocumentType("DNI");
                    customer.setEmail(p.getName());
                    customer.setName(p.getLastname().concat(", ").concat(p.getName()));
                    customer.setPhone(p.getPhone());
                    customer.setNumberDocument(p.getDni());
                    customer.setId(p.getId());
                    return Mono.just(customer);
                });
    }

    @Override
    public Mono<Customer> findCustomerCommercialById(String id) {
        return webClient.baseUrl(URL_GATEWAY).build().get().uri("/api/customer/enterprise/".concat(id))
                .retrieve()
                .bodyToMono(CommercialCustomerDto.class)
                .onErrorResume(error -> {
                    WebClientResponseException resp = (WebClientResponseException) error;
                    if (resp.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("Enterprise customer not found"));
                    }
                    return Mono.error(error);
                }).flatMap(p -> {
                    Customer customer = new Customer();
                    customer.setDocumentType("RUC");
                    customer.setEmail(p.getEmail());
                    customer.setName(p.getReasonSocial());
                    customer.setPhone(p.getPhone());
                    customer.setNumberDocument(p.getRuc());
                    customer.setId(p.getId());
                    return Mono.just(customer);
                });
    }
}
