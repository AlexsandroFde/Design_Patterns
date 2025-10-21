package com.example.patterns.mapper;

import com.example.patterns.model.Order;
import com.example.patterns.model.OrderItem;
import com.example.patterns.persistence.OrderDTO;

import java.util.stream.Collectors;

public class OrderDataMapper {
    public Order toDomain(OrderDTO orderDto) {
        Order order = new Order(orderDto.id);
        if (orderDto.items != null) {
            for (OrderDTO.OrderItemDTO orderItemDto : orderDto.items) {
                order.addItem(new OrderItem(orderItemDto.productId, orderItemDto.quantity, orderItemDto.price));
            }
        }
        return order;
    }

    public OrderDTO toDTO(Order order) {
        OrderDTO orderDto = new OrderDTO();
        orderDto.id = order.getId();
        orderDto.items = order.getItems().stream().map(orderItem -> {
            OrderDTO.OrderItemDTO orderItemDto = new OrderDTO.OrderItemDTO();
            orderItemDto.productId = orderItem.getProductId();
            orderItemDto.quantity = orderItem.getQuantity();
            orderItemDto.price = orderItem.getPrice();
            return orderItemDto;
        }).collect(Collectors.toList());
        return orderDto;
    }
}
