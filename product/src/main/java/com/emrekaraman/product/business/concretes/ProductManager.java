package com.emrekaraman.product.business.concretes;

import com.emrekaraman.product.business.dto.ProductDto;
import com.emrekaraman.product.business.dto.ProductGetDto;
import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.business.abstracts.ProductService;
import com.emrekaraman.product.client.abstracts.SellerClientService;
import com.emrekaraman.product.core.constants.Messages;
import com.emrekaraman.product.core.utilities.*;
import com.emrekaraman.product.dao.ProductDao;
import com.emrekaraman.product.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductManager implements ProductService {

    private final ProductDao productDao;
    private final SellerClientService sellerClientService;
    private final ModelMapper modelMapper;

    public ProductManager(ProductDao productDao, SellerClientService sellerClientService, ModelMapper modelMapper) {
        this.productDao = productDao;
        this.sellerClientService = sellerClientService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Result save(ProductDto productDto) {
        try {
            Product product = dtoToModel(productDto);
            productDao.save(product);
            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception ex){
            return new ErrorResult(ex.getMessage());
        }
    }

    @Override
    public Result delete(Long id) {

        if (!existById(id)){return new ErrorDataResult(Messages.NOT_FOUND);}

        try {
            productDao.deleteById(id);
            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception ex){
            return new ErrorResult(ex.getMessage());
        }
    }

    @Override
    public Result update(ProductDto productDto) {

        if (!existById(productDto.getId())){return new ErrorDataResult(Messages.NOT_FOUND);}

        try {
            Product product = dtoToModel(productDto);
            productDao.save(product);
            return new SuccessResult(Messages.SUCCESS);
        }catch (Exception ex){
            return new ErrorResult(ex.getMessage());
        }
    }

    @Override
    public DataResult<ProductDto> getById(Long id) {

        if (!existById(id)){return new ErrorDataResult(Messages.NOT_FOUND);}

        try {
            Optional<Product> product = Optional.of(productDao.getById(id));
            ProductGetDto response = modelMapper.map(product.get(),ProductGetDto.class);
            String sellerName = getSeller(String.valueOf(product.get().getSellerId())).getData().getFullname();
            response.setSellerName(sellerName);
            return product.get() != null ? new SuccessDataResult(response,Messages.SUCCESS) : new ErrorDataResult(Messages.FAILED);
        }catch (Exception ex){
            return new ErrorDataResult(ex.getMessage());
        }
    }

    @Override
    public DataResult<ProductDto> getAll() {
        try {
            List<ProductDto> productDtos = new ArrayList<>();
            List<Product> products = productDao.findAll();
            for (Product item : products){
                productDtos.add(modelToDto(item));
            }
            return new SuccessDataResult(productDtos,Messages.SUCCESS);
        }catch (Exception ex){
            return new ErrorDataResult(ex.getMessage());
        }
    }

    @Override
    public DataResult<SellerDto> getSeller(String id){
        return sellerClientService.getSeller(id);
    }

    public boolean existById(Long id){
        return productDao.existsById(id) ? true : false;
    }

    public Product dtoToModel(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setSellerId(productDto.getSellerId());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public ProductDto modelToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setSellerId(product.getSellerId());
        productDto.setDescription(product.getDescription());
        return productDto;
    }
}
