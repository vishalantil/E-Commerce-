package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.ItemConverter;
import com.Market.E_Commerce.App.Convertor.OrderConverter;
import com.Market.E_Commerce.App.Enum.ProductStatus;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Exception.ProductNotFoundException;
import com.Market.E_Commerce.App.Exception.RequiredQuantityNotAvailable;
import com.Market.E_Commerce.App.Model.*;
import com.Market.E_Commerce.App.Repository.CartRepository;
import com.Market.E_Commerce.App.Repository.CustomerRepository;
import com.Market.E_Commerce.App.Repository.ItemRepository;
import com.Market.E_Commerce.App.Repository.ProductRepository;
import com.Market.E_Commerce.App.RequestDTO.OrderRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.ItemResponseDto;
import com.Market.E_Commerce.App.ResponseDTO.OrderResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    MailService mailService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CartRepository cartRepository;

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
                .productId(productId)
                .build();

        itemRepository.save(item);

        ItemResponseDto itemResponseDto = ItemConverter.ProducttoItemResponseDto(product);

        return itemResponseDto;
    }

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
        item.setProductId(product.getId());
        cart.getItems().add(item);

        cartRepository.save(cart);

        Mail mail = new Mail();
        mail.setReceiver(customer.getEmail());
        mail.setText(product.getName() + " is added to your cart successfully.");
        mail.setSubject("Cart Updated.");
        mailService.sendMail(mail);

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
            Product product = null;

            try {
                product = productRepository.findById(item.getProductId()).get();
            } catch (Exception e) {
                System.out.print("product does not exist for this item");
                continue;
            }
            if (product == null) continue;
            Ordered order = new Ordered();
            order.setTotalCost(item.getRequiredQuantity() * product.getPrice());
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
            orderRequestDto.setProductId(product.getId());
            orderRequestDto.setRequiredQuantity(item.getRequiredQuantity());
            orderRequestDto.setCustomerId(customer.getId());

            OrderResponseDto orderResponseDto = OrderConverter.prepareOrder(order,product,orderRequestDto);
            orderResponseDto.setCardUsedForPayment(cardUsed);
            orderResponseDto.setDeliveryCharge(10);

            orderResponseDtos.add(orderResponseDto);
        }

        int total = 0;
        String text = "";

        for(int i = 0;i < orderResponseDtos.size();i++){
            OrderResponseDto orderResponseDto = orderResponseDtos.get(i);
            text += orderResponseDto.getProductName() +" " +orderResponseDto.getTotalCost() + "\n";
            total += orderResponseDto.getTotalCost() + 10;
        }

        text += "TOTAL COST - $" + total +"(10 rupees per item delivery charges)";

        Mail mail = new Mail();
        mail.setReceiver(customer.getEmail());
        mail.setText(text);
        mail.setSubject("Checkout cart.");
        mailService.sendMail(mail);

        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        customerRepository.save(customer);

        return orderResponseDtos;
    }
}
