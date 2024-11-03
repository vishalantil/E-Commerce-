package com.Market.E_Commerce.App.Convertor;

import com.Market.E_Commerce.App.RequestDTO.UserRequestDto;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
public class UserConvertor {

    public static User UserRequestDtoToUser(UserRequestDto userRequestDto){

        return (User) User.builder()
                .username(userRequestDto.getUsername())
                .password(userRequestDto.getPassword())
                .build();

    }


}
