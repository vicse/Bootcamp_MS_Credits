package com.vos.bootcamp.mscredits.services;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.models.Customer;
import com.vos.bootcamp.mscredits.models.CustomerType;
import com.vos.bootcamp.mscredits.repositories.CreditProductRepository;
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
public class CreditProductServiceTest {

  private final CustomerType typeCustomer1 = CustomerType.builder().name("Type of customer 1").build();

  private final Customer customer1 = Customer.builder().names("Vicse").surnames("Ore Soto").numIdentityDoc("75772936")
          .email("vicseore@gmail.com").phoneNumber("945026794").address("Calle 1 El Agustino").typeCustomer(typeCustomer1).build();


  private final CreditProductType creditProductType = CreditProductType.builder().name("TARJETA DE CREDITO").build();

  private final CreditProduct creditProduct1 = CreditProduct.builder().numDocOwner("75772936")
          .accountNumber("1234-123123-123").creditAmount(1300.00).creditProductType(creditProductType).build();
  private final CreditProduct creditProduct2 = CreditProduct.builder().numDocOwner("67545688")
          .accountNumber("1234-123123-234").creditAmount(1000.00).creditProductType(creditProductType).build();
  private final CreditProduct creditProduct3 = CreditProduct.builder().numDocOwner("98787645")
          .accountNumber("1234-123123-678").creditAmount(500.00).creditProductType(creditProductType).build();


  @Mock
  private CreditProductRepository creditProductRepository;

  private CreditProductService creditProductService;

  @BeforeEach
  void SetUp(){
    creditProductService = new CreditProductServiceImpl(creditProductRepository) {
    };
  }

  @Test
  void getAll() {
    when(creditProductRepository.findAll()).thenReturn(Flux.just(creditProduct1, creditProduct2, creditProduct3));

    Flux<CreditProduct> actual = creditProductService.findAll();

    assertResults(actual, creditProduct1, creditProduct2, creditProduct3);
  }

  @Test
  void getById_whenIdExists_returnCorrectCreditProduct() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.just(creditProduct1));

    Mono<CreditProduct> actual = creditProductService.findById(creditProduct1.getId());

    assertResults(actual, creditProduct1);
  }

  @Test
  void getById_whenIdNotExist_returnEmptyMono() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.empty());

    Mono<CreditProduct> actual = creditProductService.findById(creditProduct1.getId());

    assertResults(actual);
  }

  @Test
  void create() {
    when(creditProductRepository.save(creditProduct1)).thenReturn(Mono.just(creditProduct1));

    Mono<CreditProduct> actual = creditProductService.save(creditProduct1);

    assertResults(actual, creditProduct1);
  }

  @Test
  void create_whenCustomerNotExist() {
    when(creditProductRepository.save(creditProduct3)).thenReturn(Mono.error(new Exception("Customer not exist")));

    Mono<CreditProduct> actual = creditProductService.save(creditProduct3);

    assertResults(actual, new Exception("Customer not exist"));
  }

  @Test
  void update_whenIdExists_returnUpdatedCreditProduct() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.just(creditProduct1));
    when(creditProductRepository.save(creditProduct1)).thenReturn(Mono.just(creditProduct1));

    Mono<CreditProduct> actual = creditProductService.update(creditProduct1.getId(), creditProduct1);

    assertResults(actual, creditProduct1);
  }

  @Test
  void update_whenIdNotExist_returnEmptyMono() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.empty());

    Mono<CreditProduct> actual = creditProductService.update(creditProduct1.getId(), creditProduct1);

    assertResults(actual);
  }

  @Test
  void delete_whenCreditProductExists_performDeletion() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.just(creditProduct1));
    when(creditProductRepository.delete(creditProduct1)).thenReturn(Mono.empty());

    Mono<CreditProduct> actual = creditProductService.deleteById(creditProduct1.getId());

    assertResults(actual, creditProduct1);
  }

  @Test
  void delete_whenIdNotExist_returnEmptyMono() {
    when(creditProductRepository.findById(creditProduct1.getId())).thenReturn(Mono.empty());

    Mono<CreditProduct> actual = creditProductService.deleteById(creditProduct1.getId());

    assertResults(actual);
  }

  private void assertResults(Publisher<CreditProduct> publisher, CreditProduct... expectedCreditProduct) {
    StepVerifier
            .create(publisher)
            .expectNext(expectedCreditProduct)
            .verifyComplete();
  }

  private void assertResults(Mono<CreditProduct> actual, Exception customer_not_exist) {
    StepVerifier
            .create(actual)
            .expectErrorMessage("Customer not exist")
            .verify();
  }
}
