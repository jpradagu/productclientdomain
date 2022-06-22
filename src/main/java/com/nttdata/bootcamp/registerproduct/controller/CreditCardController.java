package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.CreditCard;
import com.nttdata.bootcamp.registerproduct.service.CreditCardService;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CorporateCreditCard Controller.
 */
@RestController
@RequestMapping("/api/register/credit-card")
public class CreditCardController {

  private final Logger log = LoggerFactory.getLogger(CreditCardController.class);

  @Autowired
  private CreditCardService creditCardService;

  /**
   * FindAll CorporateCreditCard.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<CreditCard>>> findAll() {
    log.info("CorporateCreditCardController findAll ->");
    return Mono.just(
        ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(creditCardService.findAll()));
  }

  /**
   * Find CorporateCreditCard.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<CreditCard>> findById(@PathVariable String id) {
    log.info("CorporateCreditCardController findById ->");
    return creditCardService.findById(id)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Create Corporate Credit Card.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<CreditCard> corporateCreditCardMono) {
    log.info("CorporateCreditCardController create ->");
    Map<String, Object> result = new HashMap<>();
    return corporateCreditCardMono
        .flatMap(c -> creditCardService.create(c)
            .map(p -> ResponseEntity
                .created(URI.create("/api/register/credit-card/corporate/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result)))
        .onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Delete corporate credit card.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("CorporateCreditCardController delete ->");
    return creditCardService.findById(id)
        .flatMap(e -> creditCardService.delete(e)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
