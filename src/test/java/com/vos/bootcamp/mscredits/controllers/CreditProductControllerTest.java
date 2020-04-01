package com.vos.bootcamp.mscredits.controllers;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.services.CreditProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreditProductControllerTest {

  @Mock
  private CreditProductService creditProductService;
  private WebTestClient client;
  private List<CreditProduct> expectedCreditProducts;

  private final CreditProductType creditProductType1 = CreditProductType.builder().id("1").name("TARJETA DE CREDITO").build();
  private final CreditProductType creditProductType2 = CreditProductType.builder().id("2").name("ADELTANTO EFECTIVO").build();
  private final CreditProductType creditProductType3 = CreditProductType.builder().id("3").name("PERSONAL").build();

  @BeforeEach
  void setUp() {
    client = WebTestClient
            .bindToController(new CreditProductController(creditProductService))
            .configureClient()
            .baseUrl("/api/creditProducts")
            .build();

    expectedCreditProducts = Arrays.asList(
            CreditProduct.builder().id("1").numDocOwner("75772936")
                    .accountNumber("1234-123123-123").creditAmount(1300.00).creditProductType(creditProductType1).build(),
            CreditProduct.builder().id("2").numDocOwner("67545688")
                    .accountNumber("1234-123123-234").creditAmount(1000.00).creditProductType(creditProductType2).build(),
            CreditProduct.builder().id("3").numDocOwner("98787645")
                    .accountNumber("1234-123123-678").creditAmount(500.00).creditProductType(creditProductType3).build());

  }

  @Test
  void getAllCreditProducts() {
    when(creditProductService.findAll())
            .thenReturn(Flux.fromIterable(expectedCreditProducts));

    client.get()
            .uri("/")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CreditProduct.class)
            .isEqualTo(expectedCreditProducts);
  }

  @Test
  void getCreditProductById_whenCreditProductExists_returnCorrectCreditProduct() {
    CreditProduct expectedCreditProduct = expectedCreditProducts.get(0);
    when(creditProductService.findById(expectedCreditProduct.getId()))
            .thenReturn(Mono.just(expectedCreditProduct));

    client.get()
            .uri("/{id}", expectedCreditProduct.getId())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CreditProduct.class)
            .isEqualTo(expectedCreditProduct);
  }

  @Test
  void getCreditProductByAccountNumber_whenCreditProductExists_returnCorrectCreditProduct() {
    CreditProduct expectedCreditProduct = expectedCreditProducts.get(0);
    when(creditProductService.findByAccountNumber(expectedCreditProduct.getAccountNumber()))
            .thenReturn(Mono.just(expectedCreditProduct));

    client.get()
            .uri("/accountNumber/{accountNumber}", expectedCreditProduct.getAccountNumber())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CreditProduct.class)
            .isEqualTo(expectedCreditProduct);
  }

  @Test
  void getCreditProductByAccountNumber_whenCreditProductNotExist_returnNotFound() {
    String accountNumber = "NOT_EXIST_ID";
    when(creditProductService.findByAccountNumber(accountNumber))
            .thenReturn(Mono.empty());

    client.get()
            .uri("/accountNumber/{accountNumber}", accountNumber)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void getCreditProductById_whenCreditProductNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    when(creditProductService.findById(id))
            .thenReturn(Mono.empty());

    client.get()
            .uri("/{id}", id)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void addCreditProduct() {
    CreditProduct expectedCreditProduct = expectedCreditProducts.get(0);
    when(creditProductService.save(expectedCreditProduct))
            .thenReturn(Mono.just(expectedCreditProduct));

    client.post()
            .uri("/")
            .body(Mono.just(expectedCreditProduct), CreditProduct.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CreditProduct.class)
            .isEqualTo(expectedCreditProduct);
  }

  @Test
  void updateCreditProduct_whenCreditProductExists_performUpdate() {
    CreditProduct expectedCreditProduct = expectedCreditProducts.get(0);
    when(creditProductService.update(expectedCreditProduct.getId(), expectedCreditProduct))
            .thenReturn(Mono.just(expectedCreditProduct));

    client.put()
            .uri("/{id}", expectedCreditProduct.getId())
            .body(Mono.just(expectedCreditProduct), CreditProduct.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CreditProduct.class)
            .isEqualTo(expectedCreditProduct);
  }

  @Test
  void updateCreditProductType_whenCreditProductTypeNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    CreditProduct expectedCreditProduct = expectedCreditProducts.get(0);
    when(creditProductService.update(id, expectedCreditProduct))
            .thenReturn(Mono.empty());

    client.put()
            .uri("/{id}", id)
            .body(Mono.just(expectedCreditProduct), CreditProduct.class)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void deleteCreditProduct_whenCreditProduct_performDeletion() {
    CreditProduct creditProductToDelete = expectedCreditProducts.get(0);
    when(creditProductService.deleteById(creditProductToDelete.getId()))
            .thenReturn(Mono.just(creditProductToDelete));

    client.delete()
            .uri("/{id}", creditProductToDelete.getId())
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  void deleteCreditProduct_whenIdNotExist_returnNotFound() {
    CreditProduct creditProductToDelete = expectedCreditProducts.get(0);
    when(creditProductService.deleteById(creditProductToDelete.getId()))
            .thenReturn(Mono.empty());

    client.delete()
            .uri("/{id}", creditProductToDelete.getId())
            .exchange()
            .expectStatus()
            .isNotFound();
  }


}
