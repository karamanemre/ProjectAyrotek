package com.emrekaraman.product.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGetDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String sellerName;
}
