package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Model.Product;
import com.Market.E_Commerce.App.ResponseDTO.ItemResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemConverter {

    public static ItemResponseDto ProducttoItemResponseDto(Product product){

        return ItemResponseDto.builder()
                .productName(product.getName())
                .productStatus(product.getProductStatus())
                .price(product.getPrice())
                .productCategory(product.getProductCategory())
                .build();
    }
}
