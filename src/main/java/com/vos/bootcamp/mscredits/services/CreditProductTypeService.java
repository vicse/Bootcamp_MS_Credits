package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProductType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditProductTypeService {

  public Flux<CreditProductType> findAll();

  public Mono<CreditProductType> findById(String id);

  public Mono<CreditProductType> save(CreditProductType creditProductType);

  public Mono<CreditProductType> update(String id, CreditProductType creditProductType);

  public Mono<CreditProductType> deleteById(String id);
}
