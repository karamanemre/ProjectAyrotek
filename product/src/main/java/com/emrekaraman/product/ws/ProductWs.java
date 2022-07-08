package com.emrekaraman.product.ws;



import com.emrekaraman.product.business.dto.ProductDto;
import com.emrekaraman.product.business.abstracts.ProductService;
import com.emrekaraman.product.business.dto.SellerDto;
import com.emrekaraman.product.core.utilities.DataResult;
import com.emrekaraman.product.core.utilities.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/productws")
public class ProductWs {

    private final ProductService productService;

    public ProductWs(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity<Result> save(@RequestBody ProductDto productDto){

        Result response = productService.save(productDto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Result> delete(@PathVariable Long id){

        Result response = productService.delete(id);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/update")
    public ResponseEntity<Result> update(@RequestBody ProductDto productDto){

        Result response = productService.update(productDto);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<DataResult<ProductDto>> getById(@PathVariable Long id){

        DataResult<ProductDto> response = productService.getById(id);
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("getAll")
    public ResponseEntity<DataResult<ProductDto>> getAll(){

        DataResult<ProductDto> response = productService.getAll();
        if (response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
