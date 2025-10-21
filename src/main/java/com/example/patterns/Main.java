package com.example.patterns;

import com.example.patterns.dao.JsonOrderDao;
import com.example.patterns.dao.JsonRawOrderDao;
import com.example.patterns.model.Order;
import com.example.patterns.model.OrderItem;
import com.example.patterns.repository.OrderRepository;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Demonstração: DAO vs Data Mapper vs Repository (PT-BR)\n");

        // 1) Exemplo de DAO que retorna objetos de domínio diretamente
        JsonOrderDao dao = new JsonOrderDao();
        List<Order> ordersFromDao = dao.findAll();
        System.out.println("[DAO] Pedidos carregados (domínio direto) = " + ordersFromDao.size());
        ordersFromDao
                .forEach(ord -> System.out.println("  order.id=" + ord.getId() + " items=" + ord.getItems().size()));

        // 2) Exemplo de Raw DAO + Data Mapper
        JsonRawOrderDao rawDao = new JsonRawOrderDao();
        List<Order> ordersFromRawDao = rawDao.findAll();
        System.out.println("[DataMapper] Orders mapeados = " + ordersFromRawDao.size());
        ordersFromRawDao
                .forEach(ord -> System.out.println("  order.id=" + ord.getId() + " items=" + ord.getItems().size()));

        // 3) Repository: trata Order como agregado, recupera/persiste como unidade
        OrderRepository repo = new OrderRepository(rawDao);
        List<Order> ordersFromRepo = repo.findAll();
        System.out.println("[Repository] Agregados carregados = " + ordersFromRepo.size());

        // Construindo um agregado e demonstrando a invariante definida no domínio
        Order newOrder = new Order("new-1");
        for (int i = 0; i < 10; i++) {
            newOrder.addItem(new OrderItem("p" + i, 1, 9.99));
        }
        System.out.println("Total do pedido antes do save = " + newOrder.total());
        repo.save(newOrder); // deve passar

        // Tentar violar a invariante do agregado (mais de 10 itens)
        try {
            newOrder.addItem(new OrderItem("overflow", 1, 1.0));
            repo.save(newOrder);
        } catch (Exception e) {
            System.out.println("Invariante do agregado violada: " + e.getMessage());
        }
    }
}
