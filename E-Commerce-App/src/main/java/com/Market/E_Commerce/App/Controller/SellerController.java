package com.Market.E_Commerce.App.Controller;

import com.Market.E_Commerce.App.Model.Seller;
import com.Market.E_Commerce.App.RequestDTO.SellerRequestDto;
import com.Market.E_Commerce.App.ResponseDTO.SellerResponseDto;
import com.Market.E_Commerce.App.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDto sellerRequestDto){
          return sellerService.addSeller(sellerRequestDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllSellers(){
        List<SellerResponseDto> sellerResponseDtoList = sellerService.getAllSellers();
        if(sellerResponseDtoList.size() == 0){
            return new ResponseEntity<>("No Sellers available", HttpStatus.OK);
        }
        return new ResponseEntity<>(sellerResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/getbyPancard")
    public ResponseEntity getbyPancard(@RequestParam("pancard") String pancard){

        SellerResponseDto sellerResponseDto;

        try{
            sellerResponseDto = sellerService.getbyPancard(pancard);
        }
        catch(Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(sellerResponseDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteById")
    public String deleteById(@RequestParam("id") int id){

        String toReturn = "";

        try{
            toReturn = sellerService.deleteById(id);
        }
        catch(Exception e){
            return e.getMessage();
        }

        return toReturn;
    }
}
