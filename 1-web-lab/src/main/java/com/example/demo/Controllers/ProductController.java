package com.example.demo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Models.Product;

@Controller
public class ProductController {
    @GetMapping("/products/{name}")
    public @ResponseBody Product getProduct(@PathVariable String name) {
        Product product = Product.getProducts().getOrDefault(name.toLowerCase(), null);
        return product;
    }
}
