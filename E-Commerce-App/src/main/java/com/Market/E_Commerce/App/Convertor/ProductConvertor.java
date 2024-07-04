package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Enum.ProductStatus;
import com.Market.E_Commerce.App.Model.Product;
import com.Market.E_Commerce.App.RequestDTO.ProductRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ProductResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductConvertor {

    public static Product ProductRequestDtotoProduct(ProductRequestDto productRequestDto){

        return Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .quantity(productRequestDto.getQuantity())
                .productCategory(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto ProducttoProductResponseDto(Product product){

        return ProductResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();
    }
}
