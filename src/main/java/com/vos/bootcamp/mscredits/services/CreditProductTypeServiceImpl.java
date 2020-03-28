package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.repositories.CreditProductTypeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditProductTypeServiceImpl implements CreditProductTypeService {

  private final CreditProductTypeRepository repository;

  public CreditProductTypeServiceImpl(CreditProductTypeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Flux<CreditProductType> findAll() {
    return repository.findAll();
  }

  @Override
  public Mono<CreditProductType> findById(String id) {
    return repository.findById(id);
  }

  @Override
  public Mono<CreditProductType> save(CreditProductType creditProductType) {
    return repository.save(creditProductType);
  }

  @Override
  public Mono<CreditProductType> update(String id, CreditProductType creditProductType) {
    return repository.findById(id).flatMap(creditProductTypeDB -> {

      if (creditProductType.getName() == null) {
        creditProductTypeDB.setName(creditProductTypeDB.getName());
      } else {
        creditProductTypeDB.setName(creditProductType.getName());
      }

      return repository.save(creditProductTypeDB);

    });
  }

  @Override
  public Mono<CreditProductType> deleteById(String id) {
    return repository.findById(id)
            .flatMap(creditProductType -> repository.delete(creditProductType)
            .then(Mono.just(creditProductType))
            );
  }
}
