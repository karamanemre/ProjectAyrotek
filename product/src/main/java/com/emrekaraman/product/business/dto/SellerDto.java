package com.emrekaraman.product.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDto {
    private Long id;
    private String fullname;
    private String email;
    private String password;
}
