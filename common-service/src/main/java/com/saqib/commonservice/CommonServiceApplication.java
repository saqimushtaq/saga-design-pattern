package com.saqib.commonservice;

import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommonServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommonServiceApplication.class, args);
  }

}
