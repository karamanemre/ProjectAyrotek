package com.emrekaraman.product.business.abstracts;


import com.emrekaraman.product.business.dto.ProductDto;
import com.emrekaraman.product.business.dto.ProductGetDto;
import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.core.utilities.DataResult;
import com.emrekaraman.product.core.utilities.Result;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProductService {

    Result save(ProductDto productDto);
    Result delete(Long id);
    Result update(ProductDto productDto);
    DataResult<ProductDto> getById(Long id);
    DataResult<ProductGetDto> getAll();
    DataResult<SellerDto> getSeller(String id);
}
