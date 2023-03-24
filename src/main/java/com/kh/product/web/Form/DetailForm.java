package com.kh.product.web.Form;

import lombok.Data;

@Data
public class DetailForm {
    private Long pid;
    private String pname;
    private Long quantity;
    private Long price;
}
