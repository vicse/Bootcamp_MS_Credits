package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.repositories.CreditProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Service
public class CreditProductServiceImpl implements CreditProductService {

  private final CreditProductRepository repository;

  public CreditProductServiceImpl(CreditProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public Mono<Boolean> existsCustomer(String id) {
    return WebClient
            .create()
            .get()
            .uri("http://localhost:8001/api/customers/" + id + "/exist")
            .retrieve()
            .bodyToMono(Boolean.class);

  }

  @Override
  public Flux<CreditProduct> findAll() {
    return repository.findAll();
  }

  @Override
  public Mono<CreditProduct> findById(String id) {
    return repository.findById(id);
  }

  @Override
  public Mono<CreditProduct> save(CreditProduct creditProduct) {

    creditProduct.setCreatedAt(new Date());
    Mono<Boolean> existsCus = this.existsCustomer(creditProduct.getNumDocOwner());

    return existsCus.flatMap(resp -> {
      if (resp) {
        return repository.save(creditProduct);
      } else {
        log.error("Customer not exists!!!");
        return Mono.error(new Exception("Customer not exist"));
      }
    });
  }

  @Override
  public Mono<CreditProduct> update(String id, CreditProduct creditProduct) {
    creditProduct.setUpdatedAt(new Date());
    return repository.findById(id)
            .flatMap(creditProductDB -> {

              if (creditProduct.getAccountNumber() == null) {
                creditProductDB.setAccountNumber(creditProductDB.getAccountNumber());
              } else {
                creditProductDB.setAccountNumber(creditProduct.getAccountNumber());
              }

              if (creditProduct.getCreditAmount() == null) {
                creditProductDB.setCreditAmount(creditProductDB.getCreditAmount());
              } else {
                creditProductDB.setCreditAmount(creditProduct.getCreditAmount());
              }

              if (creditProduct.getCreditProductType() == null) {
                creditProductDB.setCreditProductType(creditProductDB.getCreditProductType());
              } else {
                creditProductDB.setCreditProductType(creditProduct.getCreditProductType());
              }

              return repository.save(creditProductDB);
            });
  }

  @Override
  public Mono<CreditProduct> deleteById(String id) {
    return repository.findById(id)
            .flatMap(creditProduct -> repository.delete(creditProduct)
            .then(Mono.just(creditProduct)))
            ;
  }



}
