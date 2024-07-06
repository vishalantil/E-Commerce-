package com.Market.E_Commerce.App.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {

    private String productName;

    private Date orderDate;

    private int totalCost;

    private int itemPrice;

    private int quantityOrdered;

    private int deliveryCharge;

    private String cardUsedForPayment;
}
