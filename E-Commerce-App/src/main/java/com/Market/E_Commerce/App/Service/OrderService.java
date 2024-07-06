package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.OrderConverter;
import com.Market.E_Commerce.App.Enum.ProductStatus;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Exception.ProductNotFoundException;
import com.Market.E_Commerce.App.Exception.RequiredQuantityNotAvailable;
import com.Market.E_Commerce.App.Model.*;
import com.Market.E_Commerce.App.Repository.CustomerRepository;
import com.Market.E_Commerce.App.Repository.ProductRepository;
import com.Market.E_Commerce.App.RequestDTO.OrderRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ItemResponseDto;
import com.Market.E_Commerce.App.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemService itemService;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, RequiredQuantityNotAvailable {

        Customer customer;

        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer Id.");
        }

        Product product;

        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
            throw new ProductNotFoundException("Invalid Product Id.");
        }

        if(product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new RequiredQuantityNotAvailable("Sorry! Required Quantity not available.");
        }

        ItemResponseDto itemResponseDto = itemService.viewItem(orderRequestDto.getProductId());
        Item item = product.getItem();

        int totalCost = orderRequestDto.getRequiredQuantity() * product.getPrice();
        int deliveryCharge = totalCost < 500 ? 0 : 50;

        Ordered order = Ordered.builder()
                .totalCost(totalCost)
                .deliveryCharge(deliveryCharge)
                .customer(customer)
                .build();

        order.getItems().add(item);

        Card card = customer.getCards().get(0);
        String cardUsed = "";
        int cardLength = card.getCardNo().length();

        for(int i = 0;i < cardLength - 4;i++){
            cardUsed += 'X';
        }

        cardUsed += card.getCardNo().substring(0,cardLength - 4);

        order.setCardUsedForPayment(cardUsed);

        customer.getOrders().add(order);

        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0) product.setProductStatus(ProductStatus.OUT_OF_STOCK);

        product.setQuantity(leftQuantity);

        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());

        item.setOrder(order);

        customerRepository.save(customer);
        productRepository.save(product);

        OrderResponseDto orderResponseDto = OrderConverter.prepareOrder(order,product,orderRequestDto);
        orderResponseDto.setCardUsedForPayment(cardUsed);
        orderResponseDto.setDeliveryCharge(deliveryCharge);

        return orderResponseDto;
    }
}
