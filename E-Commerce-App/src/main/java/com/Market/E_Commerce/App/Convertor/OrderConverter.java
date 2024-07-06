package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Model.Card;
import com.Market.E_Commerce.App.Model.Ordered;
import com.Market.E_Commerce.App.Model.Product;
import com.Market.E_Commerce.App.RequestDTO.OrderRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.OrderResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderConverter {

    public static OrderResponseDto prepareOrder(Ordered order, Product product,
                                                OrderRequestDto orderRequestDto){
        //you have to enter the card used and deliverycharge by yourself
        return OrderResponseDto.builder()
                .productName(product.getName())
                .orderDate(order.getOrderDate())
                .quantityOrdered(orderRequestDto.getRequiredQuantity())
                .itemPrice(product.getPrice())
                .totalCost(order.getTotalCost())
                .build();
    }

}
