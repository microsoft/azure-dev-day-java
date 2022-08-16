package com.example.demo.Models;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private static final Map<String, Product> products = new HashMap<String, Product>();

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static Map<String, Product> getProducts() {
        if (products.isEmpty()) {
            products.put("baseball", new Product("Baseball", 10.95));
            products.put("bat", new Product("Bat", 25.33));
            products.put("glove", new Product("Glove", 32.99));
            products.put("helmet", new Product("Helmet", 65.16));
            products.put("kneePad", new Product("KneePad", 19.02));
            products.put("shoes", new Product("Shoes", 115.56));
            products.put("socks", new Product("Socks", 17.01));
        }

        return products;
    }
}