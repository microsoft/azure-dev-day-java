package com.example.demo.Repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.Models.Order;

public interface OrderRepository extends CrudRepository<Order, UUID> {
}