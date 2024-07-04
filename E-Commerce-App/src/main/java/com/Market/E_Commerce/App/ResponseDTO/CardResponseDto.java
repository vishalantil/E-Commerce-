package com.Market.E_Commerce.App.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {
    
    private String name;
    
    List<CardInfoDto> cardInfoDtos;
}
