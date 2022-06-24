package com.nttdata.bootcamp.registerproduct.service;

import com.nttdata.bootcamp.registerproduct.model.PaymentWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * PaymentWalletService.
 */
@Service
public class PaymentWalletService {
  private static final Logger logger = LoggerFactory.getLogger(PaymentWalletService.class);

  private final KafkaTemplate<String, PaymentWallet> kafkaTemplate;

  public PaymentWalletService(KafkaTemplate<String, PaymentWallet> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendPayment(PaymentWallet t, String topic) {
    logger.info("Send payment -> {}", t);
    this.kafkaTemplate.send(topic, t);
  }
}
