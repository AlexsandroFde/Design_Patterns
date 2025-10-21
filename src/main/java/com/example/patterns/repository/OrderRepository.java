package com.example.patterns.repository;

import com.example.patterns.dao.RawOrderDao;
import com.example.patterns.model.Order;

import java.util.List;

public class OrderRepository {
    private final RawOrderDao rawDao;

    public OrderRepository(RawOrderDao rawDao) {
        this.rawDao = rawDao;
    }

    public List<Order> findAll() {
        return rawDao.findAll();
    }

    public Order findById(String id) {
        return rawDao.findById(id);
    }

    public void save(Order order) {
        if (order.getItems().size() > 10) {
            throw new IllegalStateException("Cannot save: order violates max items invariant");
        }
        rawDao.save(order);
    }
}
