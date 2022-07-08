package com.emrekaraman.product.client.abstracts;

import com.emrekaraman.product.business.dto.SellerDto;
import org.springframework.http.ResponseEntity;

public interface SellerClientService {
    ResponseEntity<SellerDto> getSeller(String sellerId);
}
