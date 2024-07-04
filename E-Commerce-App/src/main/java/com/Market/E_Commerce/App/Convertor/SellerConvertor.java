package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Model.Seller;
import com.Market.E_Commerce.App.RequestDTO.SellerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.SellerResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
//Utility class is the class whose object will never be made and we will only use its methods.
public class SellerConvertor {

    public static Seller SellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){

        return Seller.builder()
                .name(sellerRequestDto.getName())
                .email(sellerRequestDto.getEmail())
                .mobNo(sellerRequestDto.getMobNo())
                .panNo(sellerRequestDto.getPanNo())
                .build();
    }

    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){

        return SellerResponseDto.builder()
                .name(seller.getName())
                .email(seller.getEmail())
                .mobNo(seller.getMobNo())
                .build();
    }
}
