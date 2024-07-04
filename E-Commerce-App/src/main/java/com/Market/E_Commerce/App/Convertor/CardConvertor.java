package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.Model.Card;
import com.Market.E_Commerce.App.RequestDTO.CardRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.CardResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardConvertor {

    public static Card CardRequestDtoToCard(CardRequestDto cardRequestDto){

        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .build();
    }

}
