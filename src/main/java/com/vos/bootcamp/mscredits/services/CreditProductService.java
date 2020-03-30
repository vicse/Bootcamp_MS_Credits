package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditProductService {

  public Flux<CreditProduct> findAll();

  public Mono<CreditProduct> findById(String id);

  public Mono<CreditProduct> save(CreditProduct creditProduct);

  public Mono<CreditProduct> update(String id, CreditProduct creditProduct);

  public Mono<CreditProduct> deleteById(String id);

  public Mono<Boolean> existsCustomer(String id);

}
