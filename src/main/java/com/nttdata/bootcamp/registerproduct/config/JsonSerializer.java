package com.nttdata.bootcamp.registerproduct.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * JsonSerializer.
 */
public class JsonSerializer<T> implements Serializer<T> {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void configure(Map<String, ?> configs, boolean isKey) {
    Serializer.super.configure(configs, isKey);
  }

  @Override
  public byte[] serialize(String topic, T data) {
    if (data == null) {
      return new byte[0];
    }
    try {
      return objectMapper.writeValueAsBytes(data);
    } catch (Exception e) {
      throw new SerializationException("Error serializing JSON message", e);
    }
  }

}
