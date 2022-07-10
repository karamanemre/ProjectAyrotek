package com.emrekaraman.product.client.concretes;

import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.client.abstracts.SellerClientService;
import com.emrekaraman.product.core.constants.Messages;
import com.emrekaraman.product.core.utilities.DataResult;
import com.emrekaraman.product.core.utilities.ErrorDataResult;
import com.emrekaraman.product.core.utilities.SuccessDataResult;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Service
public class SellerClientManager implements SellerClientService {

    private final String URL = "http://localhost:8085/api/userws/getById/";
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;


    public SellerClientManager(RestTemplate restTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<SellerDto> getSeller(@PathVariable Long sellerId) {
        Gson gson = new Gson();
        String res = restTemplate.getForObject(URL+sellerId, String.class);
        DataResult dataResult = gson.fromJson(res,DataResult.class);
        SellerDto sellerDto = modelMapper.map(dataResult.getData(),SellerDto.class);
        if (dataResult.getData() != null){
            return new SuccessDataResult(sellerDto, Messages.SUCCESS);
        }
        return new ErrorDataResult(sellerDto, Messages.SUCCESS);
    }
}
