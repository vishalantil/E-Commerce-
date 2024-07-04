package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Enum.ProductCategory;
import com.Market.E_Commerce.App.RequestDTO.ProductRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ProductResponseDto;
import com.Market.E_Commerce.App.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto){

        ProductResponseDto productResponseDto;

        try{
            productResponseDto = productService.addProduct(productRequestDto);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(productResponseDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/category/{productCategory}")
    public List<ProductResponseDto> getAllProductsByCategory(@PathVariable("productCategory") ProductCategory productCategory){

        return productService.getProductByCategory(productCategory);
    }
}
