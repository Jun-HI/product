package com.kh.product.web.Form;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SaveForm {

  @NotNull
  @Size(min = 2, max = 10, message = "2~10자리 문자열")
  private String pname;
  @NotNull(message = "공백불가")
  @Positive
  @Max(1000)
  private Long quantity;
  @NotNull
  @Positive
  @Min(1000)
  private Long price;

}






