package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.PersonalAccount;
import com.nttdata.bootcamp.registerproduct.service.PersonalAccountService;
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
 * PersonalAccount controller.
 */
@RestController
@RequestMapping("/api/register/account/personal")
public class PersonalAccountController {

  private final Logger log = LoggerFactory.getLogger(PersonalAccountController.class);

  @Autowired
  private PersonalAccountService personalAccountService;

  /**
   * FindAll Personal account.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<PersonalAccount>>> findAll() {
    log.info("PersonalAccountController findAll ->");
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(personalAccountService.findAll()));
  }

  /**
   * Find Personal account.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<PersonalAccount>> findById(@PathVariable String id) {
    log.info("PersonalAccountController findById ->");
    return personalAccountService.findById(id)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Create Personal account.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<PersonalAccount> accountBankMono) {
    log.info("PersonalAccountController create ->");
    Map<String, Object> result = new HashMap<>();
    return accountBankMono.flatMap(c -> personalAccountService
            .create(c)
            .map(p -> ResponseEntity
                .created(URI.create("/api/register/account/personal/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result)))
        .onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * update PersonalAccount.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Map<String, Object>>> update(
      @Valid @RequestBody Mono<PersonalAccount> accountBankMono, @PathVariable String id) {
    log.info("PersonalAccountController update ->");
    Map<String, Object> result = new HashMap<>();
    return accountBankMono.flatMap(c -> personalAccountService.update(c, id).flatMap(p -> {
      result.put("data", p);
      return Mono.just(ResponseEntity.ok().body(result));
    })).onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Delete Personal account.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>>  delete(@PathVariable String id) {
    log.info("PersonalAccountController delete ->");
    return personalAccountService.findById(id)
        .flatMap(e -> personalAccountService.delete(e)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
