package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.event.Event;
import com.nttdata.bootcamp.registerproduct.event.PaymentWalletEvent;
import com.nttdata.bootcamp.registerproduct.model.PaymentWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * PaymentWalletService.
 */
@Service
public class PaymentWalletService {
  private static final Logger logger = LoggerFactory.getLogger(PaymentWalletService.class);

  @Autowired
  private KafkaTemplate<String, Event<?>> kafkaTemplate;


  /**
   * sendPayment.
   */
  public void sendPayment(PaymentWallet t, String topic) {
    PaymentWalletEvent event = new PaymentWalletEvent();
    event.setData(t);
    logger.info("Send payment -> {}", event);
    this.kafkaTemplate.send(topic, event);
  }
}
