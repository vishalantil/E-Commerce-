package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.CustomerConvertor;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Model.Cart;
import com.Market.E_Commerce.App.Model.Customer;
import com.Market.E_Commerce.App.Repository.CustomerRepository;
import com.Market.E_Commerce.App.RequestDTO.CustomerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.CustomerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public String addCustomer(CustomerRequestDto customerRequestDto){

        Customer customer = CustomerConvertor.CustomerRequestDtotoCustomer(customerRequestDto);

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartTotal(0);

        customer.setCart(cart);

        customerRepository.save(customer);
        return "Welcome to E-Commerce app.";
    }

    public CustomerResponseDto getCustomer(int CustomerId) throws CustomerNotFoundException {

        Customer customer;

        try{
           customer = customerRepository.findById(CustomerId).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid Customer ID.");
        }

        CustomerResponseDto customerResponseDto = CustomerConvertor.CustomerToCustomerResponseDto(customer);

        return customerResponseDto;
    }

    public List<CustomerResponseDto> getAllCustomers(){

        List<Customer> list = customerRepository.findAll();

        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();

        for(Customer customer : list){
            CustomerResponseDto customerResponseDto = CustomerConvertor.CustomerToCustomerResponseDto(customer);

            customerResponseDtos.add(customerResponseDto);
        }

        return customerResponseDtos;
    }

    public String deleteById(int id) throws CustomerNotFoundException {

        Customer customer;

        try{
            customer = customerRepository.findById(id).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid ID.");
        }

        customerRepository.deleteById(id);

        return customer.getName() +" is deleted from customers.";
    }

    public CustomerResponseDto findByEmail(String email) throws CustomerNotFoundException {

        Customer customer = customerRepository.findByEmail(email);

        if(customer == null){
            throw new CustomerNotFoundException("Invalid email.");
        }

        CustomerResponseDto customerResponseDto = CustomerConvertor.CustomerToCustomerResponseDto(customer);
        return customerResponseDto;
    }

}
