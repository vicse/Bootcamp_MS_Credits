package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.repositories.CreditProductRepository;
import com.vos.bootcamp.mscredits.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Service
public class CreditProductServiceImpl implements CreditProductService {

  private final CreditProductRepository repository;
  private final CustomerRepository customerRepository;

  public CreditProductServiceImpl(CreditProductRepository repository, CustomerRepository customerRepository) {
    this.repository = repository;
    this.customerRepository = customerRepository;
  }

  @Override
  public Mono<Boolean> existsByAccountNumber(String accountNumber) {
    return repository.existsByAccountNumber(accountNumber);
  }

  @Override
  public Mono<CreditProduct> findByAccountNumber(String accountNumber) {
    return repository.findByAccountNumber(accountNumber);
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
    creditProduct.setCreditAmountAvailable(creditProduct.getCreditAmount());
    creditProduct.setDebtAmount(creditProduct.getCreditAmount());

    Mono<Boolean> existsCus = customerRepository.existsCustomer(creditProduct.getNumDocOwner());

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
    return repository.findById(id)
            .flatMap(creditProductDB -> {

              creditProductDB.setUpdatedAt(new Date());

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

              if (creditProduct.getDebtAmount() == null) {
                creditProductDB.setDebtAmount(creditProductDB.getCreditAmount());
              } else {
                creditProductDB.setDebtAmount(creditProduct.getCreditAmount());
              }

              if (creditProduct.getCreditAmountAvailable() == null) {
                creditProductDB.setCreditAmountAvailable(creditProductDB.getCreditAmountAvailable());
              } else {
                creditProductDB.setCreditAmountAvailable(creditProduct.getCreditAmountAvailable());
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
