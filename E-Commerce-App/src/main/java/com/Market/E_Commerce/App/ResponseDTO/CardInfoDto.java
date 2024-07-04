package com.Market.E_Commerce.App.ResponseDTO;

import com.Market.E_Commerce.App.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoDto {

    private String cardNo;

    private CardType cardType;
}
