package com.Market.E_Commerce.App.Repository;

import com.Market.E_Commerce.App.Model.Seller;
import com.Market.E_Commerce.App.ResponseDTO.SellerResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    Seller findByPanNo(String pancard);

}
