package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.ProductConverter;
import com.Market.E_Commerce.App.Enum.ProductCategory;
import com.Market.E_Commerce.App.Exception.SellerNotFoundException;
import com.Market.E_Commerce.App.Model.Product;
import com.Market.E_Commerce.App.Model.Seller;
import com.Market.E_Commerce.App.Repository.ProductRepository;
import com.Market.E_Commerce.App.Repository.SellerRepository;
import com.Market.E_Commerce.App.RequestDTO.ProductRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        Seller seller;

        try{
            seller = sellerRepository.findById(productRequestDto.getSellerId()).get();
        }
        catch(Exception e){
            throw new SellerNotFoundException("Invalid Seller Id");
        }

        Product product = ProductConverter.ProductRequestDtotoProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);

        sellerRepository.save(seller);

        ProductResponseDto productResponseDto = ProductConverter.ProducttoProductResponseDto(product);

        return productResponseDto;
    }

    public List<ProductResponseDto> getProductByCategory(ProductCategory productCategory){

        List<Product> products = productRepository.findAllByProductCategory(productCategory);

        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products){
            ProductResponseDto productResponseDto = ProductConverter.ProducttoProductResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }
}