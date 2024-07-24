package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Exception.ProductNotFoundException;
import com.Market.E_Commerce.App.RequestDTO.OrderRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ItemResponseDto;
import com.Market.E_Commerce.App.ResponseDTO.OrderResponseDto;
import com.Market.E_Commerce.App.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/view/{productId}")
    public ResponseEntity viewItem(@PathVariable("productId") int productId){

        ItemResponseDto itemResponseDto;

        try{
            itemResponseDto =  cartService.viewItem(productId);
        }
        catch(ProductNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(itemResponseDto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/add")
    public String addToCart(@RequestBody OrderRequestDto orderRequestDto){

        String response = "";

        try{
            response = cartService.addToCart(orderRequestDto);
        }
        catch(Exception e){
            return e.getMessage();
        }

        return response;
    }

    @PostMapping("/checkout")
    public ResponseEntity checkoutCart(@RequestParam int customerId){

        List<OrderResponseDto> orderResponseDtos ;

        try{
            orderResponseDtos = cartService.checkout(customerId);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(orderResponseDtos,HttpStatus.ACCEPTED);
    }
}
