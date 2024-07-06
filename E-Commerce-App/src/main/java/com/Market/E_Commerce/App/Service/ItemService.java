package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.ItemConverter;
import com.Market.E_Commerce.App.Exception.ProductNotFoundException;
import com.Market.E_Commerce.App.Model.Item;
import com.Market.E_Commerce.App.Model.Product;
import com.Market.E_Commerce.App.Repository.ProductRepository;
import com.Market.E_Commerce.App.ResponseDTO.ItemResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    ProductRepository productRepository;

    public ItemResponseDto viewItem(int productId) throws ProductNotFoundException {

        Product product;

        try{
            product = productRepository.findById(productId).get();
        }
        catch(Exception e){
            throw new ProductNotFoundException("Sorry! Invalid Product Id.");
        }

        Item item = Item.builder()
                .requiredQuantity(0)
                .product(product)
                .build();

        product.setItem(item);

        productRepository.save(product);

        ItemResponseDto itemResponseDto = ItemConverter.ProducttoItemResponseDto(product);

        return itemResponseDto;
    }
}
