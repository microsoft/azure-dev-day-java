package com.example.demo.Models;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Orders")
public class Order {

    @Getter
    @Setter
    @Id
    @Column(name = "Id")
    private UUID id;

    @Getter
    @Setter
    @Column(name = "CustomerName", nullable = false)
    private String customerName;

    @Getter
    @Setter
    @Column(name = "Product", nullable = false)
    private String product;

    @Getter
    @Setter
    @Column(name = "Quantity", nullable = false)
    private int quantity;
    

    public double getUnitPrice() {
        return Product.getProducts().get(product.toLowerCase()).getPrice();
    }

    public double getTotal() {
        return quantity * getUnitPrice();
    }
}
