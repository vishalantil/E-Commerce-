package com.Market.E_Commerce.App.RequestDTO;

import com.Market.E_Commerce.App.Enum.ProductCategory;
import com.Market.E_Commerce.App.Enum.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private int sellerId;

    private String name;

    private int price;

    private int quantity;

    private ProductCategory productCategory;
}
