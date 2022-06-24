package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.CustomerDebitCard;
import com.nttdata.bootcamp.registerproduct.service.CustomerDebitCardService;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CustomerDebitCard controller.
 */
@RestController
@RequestMapping("/api/register/customer/debit-card")
public class CustomerDebitCardController {

  private final Logger log = LoggerFactory.getLogger(CustomerDebitCardController.class);

  @Autowired
  private CustomerDebitCardService customerDebitCardService;

  /**
   * FindAll CustomerDebitCard.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<CustomerDebitCard>>> findAll() {
    log.info("CustomerDebitCardController findAll ->");
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(customerDebitCardService.findAll()));
  }

  /**
   * Find CustomerDebitCard.
   */
  @GetMapping("/{id}")
  @Cacheable(value = "customerDebitCard", key = "#p0")
  public Mono<ResponseEntity<CustomerDebitCard>> findById(@PathVariable String id) {
    log.info("CustomerDebitCardController findById ->");
    return customerDebitCardService.findById(id)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Create CustomerDebitCard.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<CustomerDebitCard> debitCardMono) {
    log.info("CustomerDebitCardController create ->");
    Map<String, Object> result = new HashMap<>();
    return debitCardMono
        .flatMap(c -> customerDebitCardService
            .create(c).map(p -> ResponseEntity.created(
                    URI.create("/api/register/customer/wallet/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result)))
        .onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * update CustomerDebitCard.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Map<String, Object>>> update(
      @Valid @RequestBody Mono<CustomerDebitCard> debitCardMono, @PathVariable String id) {
    log.info("CustomerDebitCardController update ->");
    Map<String, Object> result = new HashMap<>();
    return debitCardMono.flatMap(c -> customerDebitCardService.update(c, id).flatMap(p -> {
      result.put("data", p);
      return Mono.just(ResponseEntity.ok().body(result));
    })).onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Delete CustomerDebitCard.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("CustomerDebitCardController delete ->");
    return customerDebitCardService.findById(id)
        .flatMap(e -> customerDebitCardService.delete(e)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
