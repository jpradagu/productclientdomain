package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.event.Event;
import com.nttdata.bootcamp.registerproduct.event.PurchaseCoinEvent;
import com.nttdata.bootcamp.registerproduct.event.PurchaseRequestEvent;
import com.nttdata.bootcamp.registerproduct.model.PurchaseCoin;
import com.nttdata.bootcamp.registerproduct.repository.PurchaseCoinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * PurchaseCoinService.
 */
@Service
@Slf4j
public class PurchaseCoinService {

  @Autowired
  private PurchaseCoinRepository purchaseCoinRepository;

  @Autowired
  private KafkaTemplate<String, Event<?>> kafkaTemplate;


  /**
   * sendPayment.
   */
  private void sendPurchaseCoin(PurchaseCoin t) {
    PurchaseCoinEvent event = new PurchaseCoinEvent();
    event.setData(t);
    log.info("Send purchaseEvent -> {}", event);
    this.kafkaTemplate.send("purchase_coin", event);
  }

//  /**
//   * PurchaseCoin BrokerMessage.
//   */
//  @KafkaListener(topics = "update_purchase_coin",
//      containerFactory = "factPurchase")
//  public void updatePurchaseCoin(PurchaseRequestEvent purchaseCoinEvent) {
//
//    PurchaseCoin purchaseCoin = purchaseCoinEvent.getData();
//    log.info("receiver: ->" + purchaseCoin.toString());
//    //purchaseCoinRepository.save(purchaseCoin).subscribe();
//
//  }

  /**
   * findAll.
   */
  public Flux<PurchaseCoin> findAll() {
    return purchaseCoinRepository.findAll();
  }

  /**
   * create.
   */
  public Mono<PurchaseCoin> create(PurchaseCoin purchaseCoin) {
    return purchaseCoinRepository.save(purchaseCoin)
        .flatMap(purchase -> {
          sendPurchaseCoin(purchase);
          return Mono.just(purchase);
        });
  }
}
