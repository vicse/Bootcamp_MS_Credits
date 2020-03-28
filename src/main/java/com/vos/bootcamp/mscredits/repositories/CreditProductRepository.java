package com.vos.bootcamp.mscredits.repositories;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditProductRepository extends ReactiveMongoRepository<CreditProduct, String> {
}
