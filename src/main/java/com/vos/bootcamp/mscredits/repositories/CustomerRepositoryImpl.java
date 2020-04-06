package com.vos.bootcamp.mscredits.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

  private WebClient webClient = WebClient.create("http://localhost:8001/api/customers");

  @Override
  public Mono<Boolean> existsCustomer(String numDocCustomer) {
    return webClient
            .get()
            .uri("/" + numDocCustomer + "/exist")
            .retrieve()
            .bodyToMono(Boolean.class);

  }
}
