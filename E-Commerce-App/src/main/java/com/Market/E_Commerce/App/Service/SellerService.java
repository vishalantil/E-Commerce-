package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.SellerConvertor;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Exception.SellerNotFoundException;
import com.Market.E_Commerce.App.Model.Customer;
import com.Market.E_Commerce.App.Model.Mail;
import com.Market.E_Commerce.App.Model.Seller;
import com.Market.E_Commerce.App.Repository.SellerRepository;
import com.Market.E_Commerce.App.RequestDTO.SellerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.SellerResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    MailService mailService;

    public String addSeller(SellerRequestDto sellerRequestDto){

        Seller seller = SellerConvertor.SellerRequestDtoToSeller(sellerRequestDto);
        sellerRepository.save(seller);

        Mail mail = new Mail();
        mail.setReceiver(sellerRequestDto.getEmail());
        mail.setText("You have successfully registered on ecommerce as a seller.");
        mail.setSubject("Registration Successfully");
        mailService.sendMail(mail);

        return "Congrats! Now you can sell on the app.";
    }

    public List<SellerResponseDto> getAllSellers() {
        List<Seller> sellerList = sellerRepository.findAll();
        List<SellerResponseDto> sellerResponseDtoList = new ArrayList<>();
        for(Seller seller : sellerList){
            SellerResponseDto sellerResponseDto = SellerConvertor.SellerToSellerResponseDto(seller);
            sellerResponseDtoList.add(sellerResponseDto);
        }
        return sellerResponseDtoList;
    }

    //Some issue in the postman
    public SellerResponseDto getbyPancard(String pancard) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByPanNo(pancard);

        if(seller == null){
            throw new SellerNotFoundException("Invalid Pancard number.");
        }

        return SellerConvertor.SellerToSellerResponseDto(seller);
    }

    public String deleteById(int id) throws CustomerNotFoundException, SellerNotFoundException {

        Seller seller;

        try{
            seller = sellerRepository.findById(id).get();
        }
        catch(Exception e){
            throw new SellerNotFoundException("Invalid ID.");
        }

        sellerRepository.deleteById(id);

        Mail mail = new Mail();
        mail.setReceiver(seller.getEmail());
        mail.setText("Sorry to see you go.");
        mail.setSubject("Account Deleted.");
        mailService.sendMail(mail);

        return seller.getName() +" is deleted from sellers.";
    }
}
