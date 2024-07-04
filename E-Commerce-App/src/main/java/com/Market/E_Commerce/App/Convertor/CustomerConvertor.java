package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Model.Customer;
import com.Market.E_Commerce.App.RequestDTO.CustomerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.CustomerResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerConvertor {

    public static Customer CustomerRequestDtotoCustomer(CustomerRequestDto customerRequestDto){

        return Customer.builder()
                .age(customerRequestDto.getAge())
                .name(customerRequestDto.getName())
                .mobNo(customerRequestDto.getMobNo())
                .email(customerRequestDto.getEmail())
                .build();
    }

    public static CustomerResponseDto CustomerToCustomerResponseDto(Customer customer){

        return CustomerResponseDto.builder()
                .age(customer.getAge())
                .mobNo(customer.getMobNo())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }

}
