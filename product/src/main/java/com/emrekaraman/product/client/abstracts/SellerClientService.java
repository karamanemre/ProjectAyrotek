package com.emrekaraman.product.client.abstracts;

import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.core.utilities.DataResult;

public interface SellerClientService {
    DataResult<SellerDto> getSeller(String sellerId);
}
