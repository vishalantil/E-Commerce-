package com.Market.E_Commerce.App.ResponseDTO;

import com.Market.E_Commerce.App.Enum.ProductCategory;
import com.Market.E_Commerce.App.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponseDto {

    private String productName;

    private int price;

    private ProductCategory productCategory;

    private ProductStatus productStatus;
}
