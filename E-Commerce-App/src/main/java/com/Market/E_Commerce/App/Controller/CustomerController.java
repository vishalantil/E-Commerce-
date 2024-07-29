package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Model.Customer;
import com.Market.E_Commerce.App.RequestDTO.CustomerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.CustomerResponseDto;
import com.Market.E_Commerce.App.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public String addCustomer(@RequestBody CustomerRequestDto customerRequestDto){

        return customerService.addCustomer(customerRequestDto);
    }

    @GetMapping("/getbyId")
    public ResponseEntity getCustomer(@RequestParam("id") int CustomerId){

        CustomerResponseDto customerResponseDto;

        try{
           customerResponseDto = customerService.getCustomer(CustomerId);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(customerResponseDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllCustomers")
    public List<CustomerResponseDto> getAllCustomers(){

        return  customerService.getAllCustomers();

    }

    @DeleteMapping("/deleteById")
    public String deleteById(@RequestParam("id") int id){

        String toReturn = "";

        try{
            toReturn = customerService.deleteById(id);
        }
        catch(Exception e){
            return e.getMessage();
        }

        return toReturn;
    }

    @GetMapping("/getByEmail")
    public ResponseEntity findByEmail(@RequestParam("email") String email){

        CustomerResponseDto customerResponseDto;
        try{
            customerResponseDto = customerService.findByEmail(email);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customerResponseDto,HttpStatus.ACCEPTED);
    }

    @GetMapping("/getByAge")
    public ResponseEntity findAllByAge(@RequestParam("age") int age){

        List<CustomerResponseDto> customers ;

        try{
            customers = customerService.findAllByAge(age);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(customers,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteByAge")
    public String deleteByAge(@RequestParam("Age") int age){
        String toReturn = "";

        try{
            toReturn = customerService.deleteByAge(age);
        }
        catch(Exception e){
            return e.getMessage();
        }

        return toReturn;
    }

}
