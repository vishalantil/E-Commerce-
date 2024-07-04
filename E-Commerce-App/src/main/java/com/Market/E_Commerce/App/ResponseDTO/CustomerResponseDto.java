package com.Market.E_Commerce.App.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private String name;

    private int age;

    private String mobNo;

    private String email;
}
