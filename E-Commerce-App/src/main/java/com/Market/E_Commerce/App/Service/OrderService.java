package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.OrderConverter;
import com.Market.E_Commerce.App.Enum.ProductStatus;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Exception.ProductNotFoundException;
import com.Market.E_Commerce.App.Exception.RequiredQuantityNotAvailable;
import com.Market.E_Commerce.App.Model.*;
import com.Market.E_Commerce.App.Repository.CustomerRepository;
import com.Market.E_Commerce.App.Repository.OrderedRepository;
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
    MailService mailService;

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

        int totalCost = orderRequestDto.getRequiredQuantity() * product.getPrice();
        int deliveryCharge = totalCost < 500 ? 0 : 50;

        Ordered order = new Ordered();
        order.setTotalCost(totalCost);
        order.setDeliveryCharge(deliveryCharge);

        Card card = customer.getCards().get(0);
        String cardUsed = "";
        int cardLength = card.getCardNo().length();

        for(int i = 0;i < cardLength - 4;i++){
            cardUsed += 'X';
        }

        cardUsed += card.getCardNo().substring(0,cardLength - 4);

        order.setCardUsedForPayment(cardUsed);

        Item item = Item.builder()
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .build();


        item.setProductId(product.getId());
        item.setOrder(order);
        order.getItems().add(item);
        order.setCustomer(customer);

        int leftQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        if(leftQuantity <= 0) product.setProductStatus(ProductStatus.OUT_OF_STOCK);

        product.setQuantity(leftQuantity);

        customer.getOrders().add(order);
        Customer savedCustomer = customerRepository.save(customer);

        Ordered savedOrder = savedCustomer.getOrders().get(savedCustomer.getOrders().size() - 1);


        OrderResponseDto orderResponseDto = OrderConverter.prepareOrder(savedOrder,product,orderRequestDto);
        orderResponseDto.setCardUsedForPayment(cardUsed);
        orderResponseDto.setDeliveryCharge(deliveryCharge);

        Mail mail = new Mail();
        mail.setReceiver(customer.getEmail());
        mail.setText("Your order is placed successfully.");
        mail.setSubject("Order Placed.");
        mailService.sendMail(mail);

        return orderResponseDto;
    }
}
