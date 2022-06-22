package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.Credit;
import com.nttdata.bootcamp.registerproduct.service.CreditService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Credit Controller.
 */
@RestController
@RequestMapping("/api/register/credit")
public class CreditController {

  private final Logger log = LoggerFactory.getLogger(CreditController.class);

  @Autowired
  private CreditService creditService;

  /**
   * FindAll Credit.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Credit>>> findAll() {
    log.info("CreditController findAll ->");
    return Mono.just(
        ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(creditService.findAll()));
  }

  /**
   * Find Credit.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Credit>> findById(@PathVariable String id) {
    log.info("CreditController findById ->");
    return creditService.findById(id)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Create credit.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<Credit> corporateCreditMono) {
    log.info("CreditController create ->");
    Map<String, Object> result = new HashMap<>();
    return corporateCreditMono
        .flatMap(c -> creditService.create(c)
            .map(p -> ResponseEntity
                .created(URI.create("/api/register/credit/corporate/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result)))
        .onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Update credit.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Map<String, Object>>> update(
      @Valid @RequestBody Mono<Credit> creditMono, @PathVariable String id) {
    log.info("CreditController update ->");
    Map<String, Object> result = new HashMap<>();
    return creditMono.flatMap(c -> creditService.update(c, id).flatMap(p -> {
      result.put("data", p);
      return Mono.just(ResponseEntity.ok().body(result));
    })).onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Delete credit.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("CreditController delete ->");
    return creditService
        .findById(id)
        .flatMap(e -> creditService.delete(e)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
