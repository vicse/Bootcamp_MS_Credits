package com.vos.bootcamp.mscredits.controllers;

import com.vos.bootcamp.mscredits.models.CreditProductType;
import com.vos.bootcamp.mscredits.services.CreditProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/creditProductsTypes")
@Api(value = "Bank Credits Products Microservice")
public class CreditProductTypeController {

  private final CreditProductTypeService service;

  public CreditProductTypeController(CreditProductTypeService service) {
    this.service = service;
  }

  /* =========================================
    Function to List all Credit Products Types
  =========================================== */
  @GetMapping
  @ApiOperation(value = "List all CreditProductsTypes", notes = "List all CreditProductsTypes of Collections")
  public Flux<CreditProductType> getCreditProductsTypes() {
    return service.findAll();
  }

  /* ===============================================
       Function to obtain a creditProductType by id
  ============================================ */
  @GetMapping("/{id}")
  @ApiOperation(value = "Get a Credit Product Type", notes = "Get a creditProduct Type by id")
  public Mono<ResponseEntity<CreditProductType>> getByIdCreditProductType(@PathVariable String id) {
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
  @ApiOperation(value = "Create CreditProductType", notes = "Create CreditProductType, check the model please")
  public Mono<ResponseEntity<CreditProductType>> createCreditProductType(
          @Valid @RequestBody CreditProductType creditProductType) {
    return service.save(creditProductType)
            .map(creditProductTypeDB -> ResponseEntity
                    .created(URI.create("/api/creditProductsTypes/".concat(creditProductTypeDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(creditProductTypeDB)
            );
  }

  /* ====================================================
            Function to update a creditProductType by id
    ===================================================== */
  @PutMapping("/{id}")
  @ApiOperation(value = "Update a CreditProductType", notes = "Update a credit product Type by ID")
  public Mono<ResponseEntity<CreditProductType>> updateCreditProductType(
          @PathVariable String id,
          @RequestBody CreditProductType creditProductType) {

    return service.update(id, creditProductType)
            .map(creditProductTypeDB -> ResponseEntity
                    .created(URI.create("/api/creditProductsTypes/".concat(creditProductTypeDB.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(creditProductTypeDB))
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );
  }

  /* ====================================================
            Function to delete a creditProductType by id
    ===================================================== */

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete a CreditProductType", notes = "Delete a credit product Type by ID")
  public Mono<ResponseEntity<Void>> deleteByIdCreditProductType(@PathVariable String id) {
    return service.deleteById(id)
            .map(res -> ResponseEntity.ok().<Void>build())
            .defaultIfEmpty(ResponseEntity
                    .notFound()
                    .build()
            );

  }



}
