package com.example.patterns.dao;

import com.example.patterns.model.Order;

import java.util.List;

public interface RawOrderDao {
    List<Order> findAll();

    Order findById(String id);

    void save(Order order);
}
