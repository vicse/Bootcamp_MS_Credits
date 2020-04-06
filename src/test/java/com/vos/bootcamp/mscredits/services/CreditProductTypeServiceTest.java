package com.vos.bootcamp.mscredits.services;


import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.repositories.CreditProductTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreditProductTypeServiceTest {

  private final CreditProductType creditProductType1 = CreditProductType.builder().name("PERSONAL").build();
  private final CreditProductType creditProductType2 = CreditProductType.builder().name("EMPRESARIAL").build();
  private final CreditProductType creditProductType3 = CreditProductType.builder().name("TARJETA DE CREDITO").build();

  @Mock
  private CreditProductTypeRepository creditProductTypeRepository;

  private CreditProductTypeService creditProductTypeService;

  @BeforeEach
  void SetUp(){
    creditProductTypeService = new CreditProductTypeServiceImpl(creditProductTypeRepository) {
    };
  }

  @Test
  void getAll() {
    when(creditProductTypeRepository.findAll()).thenReturn(Flux.just(creditProductType1, creditProductType2, creditProductType3));

    Flux<CreditProductType> actual = creditProductTypeService.findAll();

    assertResults(actual, creditProductType1, creditProductType2, creditProductType3);
  }

  @Test
  void getById_whenIdExists_returnCorrectCreditProductType() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.just(creditProductType1));

    Mono<CreditProductType> actual = creditProductTypeService.findById(creditProductType1.getId());

    assertResults(actual, creditProductType1);
  }

  @Test
  void getById_whenIdNotExist_returnEmptyMono() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.empty());

    Mono<CreditProductType> actual = creditProductTypeService.findById(creditProductType1.getId());

    assertResults(actual);
  }

  @Test
  void create() {
    when(creditProductTypeRepository.save(creditProductType1)).thenReturn(Mono.just(creditProductType1));

    Mono<CreditProductType> actual = creditProductTypeService.save(creditProductType1);

    assertResults(actual, creditProductType1);
  }

  @Test
  void update_whenIdExists_returnUpdatedCreditProductType() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.just(creditProductType1));
    when(creditProductTypeRepository.save(creditProductType1)).thenReturn(Mono.just(creditProductType1));

    Mono<CreditProductType> actual = creditProductTypeService.update(creditProductType1.getId(), creditProductType1);

    assertResults(actual, creditProductType1);
  }

  @Test
  void update_whenIdNotExist_returnEmptyMono() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.empty());

    Mono<CreditProductType> actual = creditProductTypeService.update(creditProductType1.getId(), creditProductType1);

    assertResults(actual);
  }

  @Test
  void delete_whenCreditProductTypeExists_performDeletion() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.just(creditProductType1));
    when(creditProductTypeRepository.delete(creditProductType1)).thenReturn(Mono.empty());

    Mono<CreditProductType> actual = creditProductTypeService.deleteById(creditProductType1.getId());

    assertResults(actual, creditProductType1);
  }

  @Test
  void delete_whenIdNotExist_returnEmptyMono() {
    when(creditProductTypeRepository.findById(creditProductType1.getId())).thenReturn(Mono.empty());

    Mono<CreditProductType> actual = creditProductTypeService.deleteById(creditProductType1.getId());

    assertResults(actual);
  }

  private void assertResults(Publisher<CreditProductType> publisher, CreditProductType... expectedCreditType) {
    StepVerifier
            .create(publisher)
            .expectNext(expectedCreditType)
            .verifyComplete();
  }

}
