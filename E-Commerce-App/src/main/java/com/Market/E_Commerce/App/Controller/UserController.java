package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Convertor.UserConvertor;
import com.Market.E_Commerce.App.Repository.UserRepository;
import com.Market.E_Commerce.App.RequestDTO.UserRequestDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/registration")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserRequestDto userRequestDto) {

        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodedPassword);

        User user = UserConvertor.UserRequestDtoToUser(userRequestDto);
        userRepository.save(user);
    }
}
