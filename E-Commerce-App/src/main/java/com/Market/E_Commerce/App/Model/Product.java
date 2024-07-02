package com.Market.E_Commerce.App.Model;

import com.Market.E_Commerce.App.Enum.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;

    private int price;

    private int quantity;

    @Enumerated(EnumType.STRING)
    Category category;

    @ManyToOne
    @JoinColumn
    Seller seller;

    @OneToOne(mappedBy = "product",cascade = CascadeType.ALL)
    Item item;


}
