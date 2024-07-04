package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.SellerConvertor;
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

    public String addSeller(SellerRequestDto sellerRequestDto){

        Seller seller = SellerConvertor.SellerRequestDtoToSeller(sellerRequestDto);
        sellerRepository.save(seller);

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
    public SellerResponseDto getbyPancard(String pancard){
        return sellerRepository.findByPanNo(pancard);
    }
}
