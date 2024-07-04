package com.Market.E_Commerce.App.Repository;

import com.Market.E_Commerce.App.Enum.ProductCategory;
import com.Market.E_Commerce.App.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByProductCategory(ProductCategory productCategory);
}
