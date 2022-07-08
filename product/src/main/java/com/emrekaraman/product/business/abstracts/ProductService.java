package com.emrekaraman.product.business.abstracts;


import com.emrekaraman.product.business.dto.ProductDto;
import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.core.utilities.DataResult;
import com.emrekaraman.product.core.utilities.Result;

public interface ProductService {

    Result save(ProductDto productDto);
    Result delete(Long id);
    Result update(ProductDto productDto);
    DataResult<ProductDto> getById(Long id);
    DataResult<ProductDto> getAll();
    DataResult<SellerDto> getBySellerId(String id);
}
