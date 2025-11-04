package com.example.patterns.q1;

import com.example.patterns.dao.JsonOrderDao;
import com.example.patterns.dao.JsonRawOrderDao;
import com.example.patterns.model.Order;
import com.example.patterns.model.OrderItem;
import com.example.patterns.repository.OrderRepository;

import java.util.List;

/**
 * Demonstração da Questão 1:
 * - DAO (retorna objetos de domínio diretamente)
 * - Data Mapper (Raw DAO + Mapper) – aqui já está embutido no JsonRawOrderDao
 * - Repository (agregado + invariantes)
 */
public class PersistenceDemo {
    public static void main(String[] args) {
        System.out.println("Q1 — DAO vs Data Mapper vs Repository\n");

        // a) Comparação conceitual — deixamos no README, aqui focamos no código (item b)

        // b) Ilustrações em código
        // 1) DAO: retorna objetos de domínio diretamente
        JsonOrderDao dao = new JsonOrderDao();
        List<Order> ordersFromDao = dao.findAll();
        System.out.println("[DAO] Pedidos carregados (domínio direto) = " + ordersFromDao.size());
        ordersFromDao.forEach(ord ->
                System.out.println("  order.id=" + ord.getId() + " items=" + ord.getItems().size()));

        // 2) Data Mapper: usamos um Raw DAO + mapeamento para domínio (dentro de JsonRawOrderDao)
        JsonRawOrderDao rawDao = new JsonRawOrderDao();
        List<Order> ordersFromRawDao = rawDao.findAll();
        System.out.println("[DataMapper] Orders mapeados = " + ordersFromRawDao.size());
        ordersFromRawDao.forEach(ord ->
                System.out.println("  order.id=" + ord.getId() + " items=" + ord.getItems().size()));

        // 3) Repository: trata Order como agregado, recupera/persiste como unidade
        OrderRepository repo = new OrderRepository(rawDao);
        List<Order> ordersFromRepo = repo.findAll();
        System.out.println("[Repository] Agregados carregados = " + ordersFromRepo.size());

        // c) Agregado e invariante: no repositório, não permitimos mais que 10 itens por pedido
        Order newOrder = new Order("new-1");
        for (int i = 0; i < 10; i++) {
            newOrder.addItem(new OrderItem("p" + i, 1, 9.99));
        }
        System.out.println("Total do pedido antes do save = " + newOrder.total());
        repo.save(newOrder); // deve passar

        try {
            newOrder.addItem(new OrderItem("overflow", 1, 1.0));
            repo.save(newOrder);
        } catch (Exception e) {
            System.out.println("Invariante do agregado violada: " + e.getMessage());
        }
    }
}
