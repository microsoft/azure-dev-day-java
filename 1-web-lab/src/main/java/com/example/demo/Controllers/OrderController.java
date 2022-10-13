package com.example.demo.Controllers;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.Models.Order;
import com.example.demo.Models.Product;
import com.example.demo.Repositories.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @RequestMapping
    public String list(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", Product.getProducts());

        log.info("Retrieved list of orders");

        return "index";
    }

    @PostMapping("/orders/create")
    public String create(Order order) {
        order.setId(UUID.randomUUID());
        orderRepository.save(order);

        log.info("Saved order " + order.getId());

        return "redirect:/";
    }

    @PostMapping("/orders/delete")
    public String delete(Order order) {
        orderRepository.deleteById(order.getId());

        log.info("Deleted order " + order.getId());

        return "redirect:/";
    }
}
