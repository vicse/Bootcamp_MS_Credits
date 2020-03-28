package com.vos.bootcamp.mscredits.repositories;

import com.vos.bootcamp.mscredits.models.CreditProductType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditProductTypeRepository extends ReactiveMongoRepository<CreditProductType, String> {
}
