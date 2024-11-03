package com.Market.E_Commerce.App.Repository;


import com.Market.E_Commerce.App.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
