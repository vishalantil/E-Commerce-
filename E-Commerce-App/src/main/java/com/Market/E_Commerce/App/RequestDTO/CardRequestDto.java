package com.Market.E_Commerce.App.RequestDTO;

import com.Market.E_Commerce.App.Enum.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {

    private int customerId;

    private String cardNo;

    private int cvv;

    private CardType cardType;
}
