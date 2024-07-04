package com.Market.E_Commerce.App.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {

    private String name;

    private int age;

    private String email;

    private String mobNo;

}
