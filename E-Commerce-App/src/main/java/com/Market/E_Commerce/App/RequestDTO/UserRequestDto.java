package com.Market.E_Commerce.App.RequestDTO;

import com.Market.E_Commerce.App.Enum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String username;

    private UserRole userRole;

    private String password;
}
