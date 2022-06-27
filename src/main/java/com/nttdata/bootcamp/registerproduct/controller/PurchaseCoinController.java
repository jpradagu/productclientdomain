package com.nttdata.bootcamp.registerproduct.controller;

import com.nttdata.bootcamp.registerproduct.exception.ResumenError;
import com.nttdata.bootcamp.registerproduct.model.PurchaseCoin;
import com.nttdata.bootcamp.registerproduct.service.PurchaseCoinService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * PurchaseCoinController.
 */
@RestController
@RequestMapping("/api/register/purchase/coin")
@Slf4j
public class PurchaseCoinController {

  @Autowired
  private PurchaseCoinService purchaseCoinService;

  /**
   * Post Purchase Coin.
   */
  @PostMapping
  public Mono<ResponseEntity<Map<String, Object>>> purchaseCoin(
      @Valid @RequestBody Mono<PurchaseCoin> purchaseCoinMono) {
    log.info("CustomerWalletController purchaseCoin ->");
    Map<String, Object> result = new HashMap<>();
    return purchaseCoinMono.flatMap(purchaseCoin -> {
      result.put("data", purchaseCoin);
      return purchaseCoinService
          .create(purchaseCoin).map(p -> ResponseEntity.ok()
              .contentType(MediaType.APPLICATION_JSON).body(result));
    }).onErrorResume(ResumenError::errorResumenException);
  }
}
