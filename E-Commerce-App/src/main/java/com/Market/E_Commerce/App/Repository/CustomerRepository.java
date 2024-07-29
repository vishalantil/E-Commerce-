package com.Market.E_Commerce.App.Repository;

import com.Market.E_Commerce.App.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    List<Customer> findAll();

    Customer findByEmail(String email);

    List<Customer> findByAge(int age);
}
