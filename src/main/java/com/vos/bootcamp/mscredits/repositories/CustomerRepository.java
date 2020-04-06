package com.vos.bootcamp.mscredits.repositories;

import reactor.core.publisher.Mono;

public interface CustomerRepository {

  public Mono<Boolean> existsCustomer(String numDocCustomer);

}
