package com.emrekaraman.product.client.concretes;

import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.client.abstracts.SellerClientService;
import com.emrekaraman.product.core.constants.Messages;
import com.emrekaraman.product.core.utilities.DataResult;
import com.emrekaraman.product.core.utilities.ErrorDataResult;
import com.emrekaraman.product.core.utilities.SuccessDataResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    public DataResult<SellerDto> getSeller(String sellerId)  {
        SellerDto res = restTemplate.getForEntity(URL+sellerId,SellerDto.class).getBody();
        if (res != null){
            return new SuccessDataResult(res, Messages.SUCCESS);
        }
        return new ErrorDataResult(res, Messages.SUCCESS);
    }
}
