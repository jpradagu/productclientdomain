package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.CustomerWallet;
import com.nttdata.bootcamp.registerproduct.model.PaymentWallet;
import com.nttdata.bootcamp.registerproduct.service.CustomerWalletService;
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
 * CustomerWallet controller.
 */
@RestController
@RequestMapping("/api/register/customer/wallet")
public class CustomerWalletController {

  private final Logger log = LoggerFactory.getLogger(CustomerWalletController.class);

  @Autowired
  private CustomerWalletService customerWalletService;

  /**
   * FindAll CustomerWallet.
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<CustomerWallet>>> findAll() {
    log.info("CustomerWalletController findAll ->");
    return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(customerWalletService.findAll()));
  }

  /**
   * Find CustomerWallet.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<CustomerWallet>> findById(@PathVariable String id) {
    log.info("CustomerWalletController findById ->");
    return customerWalletService.findById(id)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * FindByPhone CustomerWallet.
   */
  @GetMapping("/phone/{phone}")
  @Cacheable(value = "customerWallet", key = "#p0")
  public Mono<ResponseEntity<CustomerWallet>> findByPhone(@PathVariable String phone) {
    log.info("CustomerWalletController findByPhone ->");
    return customerWalletService.findByPhone(phone)
        .map(ce -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ce))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**
   * Create CustomerWallet.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> create(
      @Valid @RequestBody Mono<CustomerWallet> walletMono) {
    log.info("CustomerWalletController create ->");
    Map<String, Object> result = new HashMap<>();
    return walletMono
        .flatMap(c -> customerWalletService
            .create(c).map(p -> ResponseEntity.created(
                    URI.create("/api/register/customer/wallet/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON).body(result)))
        .onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * update CustomerWallet.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Map<String, Object>>> update(
      @Valid @RequestBody Mono<CustomerWallet> walletMono, @PathVariable String id) {
    log.info("CustomerWalletController update ->");
    Map<String, Object> result = new HashMap<>();
    return walletMono.flatMap(c -> customerWalletService.update(c, id).flatMap(p -> {
      result.put("data", p);
      return Mono.just(ResponseEntity.ok().body(result));
    })).onErrorResume(ResumenError::errorResumenException);
  }

  /**
   * Delete CustomerWallet.
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    log.info("CustomerWalletController delete ->");
    return customerWalletService.findById(id)
        .flatMap(e -> customerWalletService.delete(e)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Post Payment.
   */
  @PostMapping("/payment")
  public Mono<ResponseEntity<Map<String, Object>>> paymentWalletTransaction(
      @Valid @RequestBody Mono<PaymentWallet> paymentWalletMono) {
    log.info("CustomerWalletController paymentWallet ->");
    Map<String, Object> result = new HashMap<>();
    return paymentWalletMono.flatMap(paymentWallet -> {
      result.put("data", paymentWallet);
      return customerWalletService.walletTransaction(paymentWallet).map(p -> ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON).body(result));
    }).onErrorResume(ResumenError::errorResumenException);
  }


}
