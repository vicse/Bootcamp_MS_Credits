package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.utils.ICrud;
import reactor.core.publisher.Mono;

public interface CreditProductService extends ICrud<CreditProduct> {

  public Mono<Boolean> existsByAccountNumber(String accountNumber);

  public Mono<CreditProduct> findByAccountNumber(String accountNumber);

}
