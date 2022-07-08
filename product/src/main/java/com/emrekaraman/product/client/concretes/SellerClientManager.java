package com.emrekaraman.product.client.concretes;

import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.client.abstracts.SellerClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SellerClientManager implements SellerClientService {

    private final String URL = "http://localhost:8085/api/userws/getById/";
    private final RestTemplate restTemplate;

    public SellerClientManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<SellerDto> getSeller(String sellerId) {
        Object res = restTemplate.getForObject(URL+sellerId,Object.class);
        SellerDto sellerDto1 = new SellerDto();
        return ResponseEntity.ok(sellerDto1);
    }
}
