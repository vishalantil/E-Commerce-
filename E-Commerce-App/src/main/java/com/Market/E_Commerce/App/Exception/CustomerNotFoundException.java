package com.Market.E_Commerce.App.Exception;

import com.Market.E_Commerce.App.Model.Customer;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException(String message){
        super(message);
    }
}
