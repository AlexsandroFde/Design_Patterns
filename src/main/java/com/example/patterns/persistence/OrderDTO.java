package com.example.patterns.persistence;

import java.util.List;

public class OrderDTO {
    public String id;
    public List<OrderItemDTO> items;

    public static class OrderItemDTO {
        public String productId;
        public int quantity;
        public double price;
    }
}
