package com.Market.E_Commerce.App.Service;

import com.Market.E_Commerce.App.Convertor.CardConvertor;
import com.Market.E_Commerce.App.Exception.CustomerNotFoundException;
import com.Market.E_Commerce.App.Model.Card;
import com.Market.E_Commerce.App.Model.Customer;
import com.Market.E_Commerce.App.Model.Mail;
import com.Market.E_Commerce.App.Repository.CardRepository;
import com.Market.E_Commerce.App.Repository.CustomerRepository;
import com.Market.E_Commerce.App.RequestDTO.CardRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.CardInfoDto;
import com.Market.E_Commerce.App.ResponseDTO.CardResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    MailService mailService;

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {

        Customer customer;

        try{
            customer = customerRepository.findById(cardRequestDto.getCustomerId()).get();
        }
        catch(Exception e){
            throw new CustomerNotFoundException("Invalid customer Id.");
        }

        Card card = CardConvertor.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);

        customer.getCards().add(card);

        customerRepository.save(customer);

        CardResponseDto cardResponseDto = new CardResponseDto();
        cardResponseDto.setName(customer.getName());

        List<CardInfoDto> cardDtolist =  new ArrayList<>();
        for(Card card1 : customer.getCards()){
            CardInfoDto cardInfoDto = new CardInfoDto();
            cardInfoDto.setCardType(card1.getCardType());
            cardInfoDto.setCardNo(card1.getCardNo());

            cardDtolist.add(cardInfoDto);
        }

        String cardNo = "";
        String lastCardNo = card.getCardNo().substring(8);
        for(int i = 0;i < 8;i++){
            cardNo += "*";
        }

        cardNo += lastCardNo;

        Mail mail = new Mail();
        mail.setReceiver(customer.getEmail());
        mail.setText("Your card " + cardNo + " is Successfully added.");
        mail.setSubject("Card Added Successfully.");
        mailService.sendMail(mail);

        cardResponseDto.setCardInfoDtos(cardDtolist);
        return cardResponseDto;
    }
}
