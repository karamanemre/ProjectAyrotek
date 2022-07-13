package com.emrekaraman.product.business.dto;

import com.emrekaraman.product.core.constants.Messages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotEmpty(message =  Messages.NOTNULL)
    private String name;

    @NotEmpty(message =  Messages.NOTNULL)
    private String description;

    @NotNull(message =  Messages.NOTNULL)
    private Double price;

    @NotNull(message = Messages.NOTNULL)
    private Long sellerId;
}
