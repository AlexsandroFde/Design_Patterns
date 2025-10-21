package com.example.patterns.dao;

import com.example.patterns.mapper.OrderDataMapper;
import com.example.patterns.model.Order;
import com.example.patterns.persistence.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class JsonRawOrderDao implements RawOrderDao {
    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final OrderDataMapper orderDataMapper;

    public JsonRawOrderDao() {
        this(new OrderDataMapper());
    }

    public JsonRawOrderDao(OrderDataMapper orderDataMapper) {
        this.orderDataMapper = orderDataMapper;
    }

    private List<OrderDTO> readAllOrderDtos() {
        try (InputStream is = getClass().getResourceAsStream("/data/orders.json")) {
            return jsonMapper.readValue(is, new TypeReference<List<OrderDTO>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        return readAllOrderDtos()
                .stream()
                .map(orderDataMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Order findById(String id) {
        OrderDTO orderDto = readAllOrderDtos().stream()
                .filter(d -> d.id.equals(id)
                ).findFirst()
                .orElse(null);
        return orderDto == null ? null : orderDataMapper.toDomain(orderDto);
    }

    @Override
    public void save(com.example.patterns.model.Order order) {
        com.example.patterns.persistence.OrderDTO orderDto = orderDataMapper.toDTO(order);
        System.out.println("[RawDAO] Salvando DTO id=" + orderDto.id + " items=" + orderDto.items.size());
    }
}
