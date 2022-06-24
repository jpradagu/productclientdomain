package com.nttdata.bootcamp.registerproduct.config;

import com.nttdata.bootcamp.registerproduct.model.PaymentWallet;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * KafkaProducerConfig.
 */
@Configuration
public class KafkaProducerConfig {

  @Value("${spring.kafka.producer.bootstrap-servers}")
  private String bootstrapServerConfig;


  /**
   * producerFactoryJson.
   */
  public ProducerFactory<String, PaymentWallet> producerFactoryJson() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(config);
  }

  /**
   * kafkaTemplateJson.
   */
  @Bean
  public KafkaTemplate<String, PaymentWallet> kafkaTemplateJson() {
    return new KafkaTemplate<>(producerFactoryJson());
  }
}
