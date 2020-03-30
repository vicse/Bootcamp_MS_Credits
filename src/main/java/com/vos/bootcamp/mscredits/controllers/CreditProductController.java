package com.vos.bootcamp.mscredits.controllers;

import com.vos.bootcamp.mscredits.models.CreditProduct;
import com.vos.bootcamp.mscredits.services.CreditProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/creditProducts")
@Api(value = "Bank Credits Products Microservice")
public class CreditProductController {

  private final CreditProductService service;

  public CreditProductController(CreditProductService service) {
    this.service = service;
  }

  /* =========================================
   Function to List all Credit Products Types
 =========================================== */

  @GetMapping
  @ApiOperation(value = "List all CreditProducts", notes = "List all CreditProducts of Collections")
  public Flux<CreditProduct> getCreditProducts() {
    return service.findAll();
  }

  /* ===============================================
       Function to obtain a creditProductType by id
  ============================================ */

  @GetMapping("/{id}")
  @ApiOperation(value = "Get a Credit Product", notes = "Get a creditProduct by id")
  public Mono<ResponseEntity<CreditProduct>> getByIdCreditProduct(@PathVariable String id) {
    return service.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );
  }

  /* ===============================================
          Function to create a creditProductType
  =============================================== */

  @PostMapping
  @ApiOperation(value = "Create Credit Product",
          notes = "Create CreditProduct, check the model please")

  public Mono<ResponseEntity<CreditProduct>> createCreditProduct(
          @Valid @RequestBody CreditProduct creditProduct) {
    return service.save(creditProduct)
            .map(creditProductDB -> ResponseEntity
                    .created(URI.create("/api/creditProducts/".concat(creditProductDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(creditProductDB)
            );
  }


  /* ====================================================
            Function to update a creditProductType by id
    ===================================================== */
  @PutMapping("/{id}")
  @ApiOperation(value = "Update a Credit Product", notes = "Update a credit product by ID")
  public Mono<ResponseEntity<CreditProduct>> updateCreditProduct(
          @PathVariable String id,
          @RequestBody CreditProduct creditProduct) {

    return service.update(id, creditProduct)
            .map(creditProductDB -> ResponseEntity
                    .created(URI.create("/api/creditProducts/".concat(creditProductDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(creditProductDB))
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );
  }

  /* ====================================================
            Function to delete a creditProductType by id
    ===================================================== */

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete a Credit Product", notes = "Delete a credit product by ID")
  public Mono<ResponseEntity<Void>> deleteByIdCreditProduct(@PathVariable String id) {
    return service.deleteById(id)
            .map(res -> ResponseEntity.ok().<Void>build())
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );

  }


}
