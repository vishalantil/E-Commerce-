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
import com.Market.E_Commerce.App.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    public String addToCart(OrderRequestDto orderRequestDto) throws Exception {
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

        if(product.getProductStatus().equals(ProductStatus.OUT_OF_STOCK)){
            throw new Exception("Product is out of stock");
        }

        if(product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new RequiredQuantityNotAvailable("Sorry! Required Quantity not available.");
        }

        Cart cart = customer.getCart();

        int newCost = cart.getCartTotal() + orderRequestDto.getRequiredQuantity() * product.getPrice();
        cart.setCartTotal(newCost);

        Item item = new Item();
        item.setRequiredQuantity(orderRequestDto.getRequiredQuantity());
        item.setCart(cart);
        item.setProduct(product);
        cart.getItems().add(item);

        customerRepository.save(customer);

        return "Item has been added to your cart.";
    }

    public List<OrderResponseDto> checkout(int customerId) throws Exception {

        Customer customer;

        try{
            customer = customerRepository.findById(customerId).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer Id.");
        }

        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
        Cart cart = customer.getCart();

        if(cart.getItems().size() == 0){
            throw new Exception("Cart is Empty!");
        }

        for(Item item : cart.getItems()){
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity() * item.getProduct().getPrice());
            order.setDeliveryCharge(10);
            order.setCustomer(customer);
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

            OrderRequestDto orderRequestDto = new OrderRequestDto();
            orderRequestDto.setProductId(item.getProduct().getId());
            orderRequestDto.setRequiredQuantity(item.getRequiredQuantity());
            orderRequestDto.setCustomerId(customer.getId());

            OrderResponseDto orderResponseDto = OrderConverter.prepareOrder(order,item.getProduct(),orderRequestDto);
            orderResponseDto.setCardUsedForPayment(cardUsed);
            orderResponseDto.setDeliveryCharge(10);

            orderResponseDtos.add(orderResponseDto);
        }

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);

        return orderResponseDtos;
    }
}
