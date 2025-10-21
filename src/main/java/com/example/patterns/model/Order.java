package com.example.patterns.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private final String id;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItem item) {
        if (items.size() + 1 > 10) {
            throw new IllegalStateException("Invariante do agregado violada: máximo 10 itens por pedido");
        }
        items.add(item);
    }

    public double total() {
        return items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
}
