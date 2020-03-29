package com.vos.bootcamp.mscredits.controllers;

import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.services.CreditProductTypeService;
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
public class CreditProductTypeControllerTest {

  @Mock
  private CreditProductTypeService creditProductTypeService;
  private WebTestClient client;
  private List<CreditProductType> expectedCreditProductsTypes;

  @BeforeEach
  void setUp() {
    client = WebTestClient
            .bindToController(new CreditProductTypeController(creditProductTypeService))
            .configureClient()
            .baseUrl("/api/creditProductsTypes")
            .build();

    expectedCreditProductsTypes = Arrays.asList(
            CreditProductType.builder().id("1").name("PERSONAL").build(),
            CreditProductType.builder().id("2").name("EMPRESARIAL").build(),
            CreditProductType.builder().id("3").name("ADELTANTO EFECTIVO").build());

  }

  @Test
  void getAllProducts() {
    when(creditProductTypeService.findAll())
            .thenReturn(Flux.fromIterable(expectedCreditProductsTypes));

    client.get()
            .uri("/")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(CreditProductType.class)
            .isEqualTo(expectedCreditProductsTypes);
  }

  @Test
  void getCreditProductTypeById_whenCreditProductTypeExists_returnCorrectCreditProductType() {
    CreditProductType expectedCreditProductType = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.findById(expectedCreditProductType.getId()))
            .thenReturn(Mono.just(expectedCreditProductType));

    client.get()
            .uri("/{id}", expectedCreditProductType.getId())
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(CreditProductType.class)
            .isEqualTo(expectedCreditProductType);
  }

  @Test
  void getCreditProductTypeById_whenCreditProductTypeNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    when(creditProductTypeService.findById(id))
            .thenReturn(Mono.empty());

    client.get()
            .uri("/{id}", id)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void addCreditProductType() {
    CreditProductType expectedCreditProductType = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.save(expectedCreditProductType))
            .thenReturn(Mono.just(expectedCreditProductType));

    client.post()
            .uri("/")
            .body(Mono.just(expectedCreditProductType), CreditProductType.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CreditProductType.class)
            .isEqualTo(expectedCreditProductType);
  }

  @Test
  void updateCreditProductType_whenCreditProductTypeExists_performUpdate() {
    CreditProductType expectedCreditProductType = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.update(expectedCreditProductType.getId(), expectedCreditProductType))
            .thenReturn(Mono.just(expectedCreditProductType));

    client.put()
            .uri("/{id}", expectedCreditProductType.getId())
            .body(Mono.just(expectedCreditProductType), CreditProductType.class)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CreditProductType.class)
            .isEqualTo(expectedCreditProductType);
  }

  @Test
  void updateCreditProductType_whenCreditProductTypeNotExist_returnNotFound() {
    String id = "NOT_EXIST_ID";
    CreditProductType expectedCreditProductType = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.update(id, expectedCreditProductType))
            .thenReturn(Mono.empty());

    client.put()
            .uri("/{id}", id)
            .body(Mono.just(expectedCreditProductType), CreditProductType.class)
            .exchange()
            .expectStatus()
            .isNotFound();
  }

  @Test
  void deleteCreditProductType_whenCreditProductType_performDeletion() {
    CreditProductType creditProductTypeToDelete = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.deleteById(creditProductTypeToDelete.getId()))
            .thenReturn(Mono.just(creditProductTypeToDelete));

    client.delete()
            .uri("/{id}", creditProductTypeToDelete.getId())
            .exchange()
            .expectStatus()
            .isOk();
  }

  @Test
  void deleteCreditProductType_whenIdNotExist_returnNotFound() {
    CreditProductType creditProductTypeToDelete = expectedCreditProductsTypes.get(0);
    when(creditProductTypeService.deleteById(creditProductTypeToDelete.getId()))
            .thenReturn(Mono.empty());

    client.delete()
            .uri("/{id}", creditProductTypeToDelete.getId())
            .exchange()
            .expectStatus()
            .isNotFound();
  }

}
