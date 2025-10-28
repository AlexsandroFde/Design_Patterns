package com.example.patterns.q3.di.before;

/**
 * Antes: Abstract Factory para criar serviços.
 * Problemas: verboso e adiciona muitas classes/interfaces para simples criação.
 */
public interface ServiceAbstractFactory {
    OrderServiceLegacy createOrderService();

    class ProdFactory implements ServiceAbstractFactory {
        @Override
        public OrderServiceLegacy createOrderService() {
            return new OrderServiceLegacy(SingletonDatabase.getInstance());
        }
    }

    class TestFactory implements ServiceAbstractFactory {
        @Override
        public OrderServiceLegacy createOrderService() {
            return new OrderServiceLegacy(new TestDatabase());
        }
    }
}

class OrderServiceLegacy {
    private final SingletonDatabase db; // acoplado ao tipo concreto

    public OrderServiceLegacy(SingletonDatabase db) {
        this.db = db;
    }

    public void place(String orderId) {
        db.save("order=" + orderId);
    }
}

class TestDatabase extends SingletonDatabase {
    // herança usada como "mock" improvisado (má prática); apenas demonstra o problema
    @Override
    public void save(String data) { System.out.println("[TestDB] (fake) " + data); }
}
