package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CreditProductServiceImpl implements CreditProductService {

  @Override
  public Flux<CreditProduct> findAll() {
    return null;
  }

  @Override
  public Mono<CreditProduct> findById(String id) {
    return null;
  }

  @Override
  public Mono<CreditProduct> save(CreditProduct creditProduct) {
    return null;
  }

  @Override
  public Mono<CreditProduct> update(String id, CreditProduct creditProduct) {
    return null;
  }

  @Override
  public Mono<CreditProduct> deleteById(String id) {
    return null;
  }
}
