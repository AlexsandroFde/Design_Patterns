package com.example.patterns.dao;

import com.example.patterns.model.Order;
import com.example.patterns.model.OrderItem;
import com.example.patterns.persistence.OrderDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class JsonOrderDao implements OrderDao {
    private final ObjectMapper jsonMapper = new ObjectMapper();

    private List<OrderDTO> readAllOrderDtos() {
        try (InputStream is = getClass().getResourceAsStream("/data/orders.json")) {
            return jsonMapper.readValue(is, new TypeReference<List<OrderDTO>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler o arquivo de pedidos", e);
        }
    }

    private Order toDomain(OrderDTO orderDto) {
        Order order = new Order(orderDto.id);
        if (orderDto.items != null) {
            for (OrderDTO.OrderItemDTO orderItemDto : orderDto.items) {
                order.addItem(new OrderItem(orderItemDto.productId, orderItemDto.quantity, orderItemDto.price));
            }
        }
        return order;
    }

    private OrderDTO toDTO(Order order) {
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

    @Override
    public List<Order> findAll() {
        return readAllOrderDtos()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(String id) {
        return readAllOrderDtos().stream()
                .filter(orderDto -> orderDto.id.equals(id))
                .findFirst()
                .map(this::toDomain)
                .orElse(null);
    }

    @Override
    public void save(Order order) {
        OrderDTO dto = toDTO(order);
        System.out.println("[DAO] Salvando DTO id=" + dto.id + " items=" + dto.items.size());
    }
}