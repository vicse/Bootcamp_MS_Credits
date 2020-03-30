package com.vos.bootcamp.mscredits.models;

import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ms_creditProducts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CreditProduct {

  @Id
  private String id;

  @Indexed(unique = true)
  @NotBlank(message = "'accountNumber' is required")
  private String accountNumber;

  @NotBlank(message = "'numDocOwner' is required")
  private String numDocOwner;

  private Double creditAmount;

  @Valid
  @DBRef
  private CreditProductType creditProductType;

  private Date createdAt;

  private Date updatedAt;


}
