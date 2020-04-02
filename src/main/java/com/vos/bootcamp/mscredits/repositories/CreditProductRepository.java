package com.vos.bootcamp.mscredits.repositories;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditProductRepository extends ReactiveMongoRepository<CreditProduct, String> {

  public Mono<CreditProduct> findByAccountNumber(String accountNumber);

  public Mono<Boolean> existsByAccountNumber(String accountNumber);

}
