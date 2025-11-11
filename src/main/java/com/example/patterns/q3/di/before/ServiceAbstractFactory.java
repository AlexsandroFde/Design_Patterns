package com.example.patterns.q3.di.before;

/**
 * Questão 3 — ANTES: Abstract Factory (separado do Singleton)
 * 
 * Problema: muita verbosidade para simplesmente criar objetos com dependências.
 * Você precisa de:
 * - 1 interface da factory
 * - N classes concretas da factory (uma por variação)
 * - Interfaces/classes para cada "família" de produtos
 * 
 * Resultado: explosão de classes apenas para criação de objetos.
 */
public interface ServiceAbstractFactory {
    DatabaseConnection createDatabase();
    NotificationService createNotifier();
    
    /**
     * Factory para ambiente de PRODUÇÃO
     */
    class ProductionFactory implements ServiceAbstractFactory {
        @Override
        public DatabaseConnection createDatabase() {
            return new PostgresConnection();
        }
        
        @Override
        public NotificationService createNotifier() {
            return new EmailNotificationService();
        }
    }
    
    /**
     * Factory para ambiente de TESTE
     */
    class TestFactory implements ServiceAbstractFactory {
        @Override
        public DatabaseConnection createDatabase() {
            return new InMemoryConnection();
        }
        
        @Override
        public NotificationService createNotifier() {
            return new MockNotificationService();
        }
    }
}

// ===== "Produtos" criados pela factory =====

interface DatabaseConnection {
    void execute(String sql);
}

class PostgresConnection implements DatabaseConnection {
    @Override
    public void execute(String sql) {
        System.out.println("[PostgreSQL] Executando: " + sql);
    }
}

class InMemoryConnection implements DatabaseConnection {
    @Override
    public void execute(String sql) {
        System.out.println("[InMemory] Executando: " + sql);
    }
}

interface NotificationService {
    void notify(String message);
}

class EmailNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("[Email] Enviando: " + message);
    }
}

class MockNotificationService implements NotificationService {
    @Override
    public void notify(String message) {
        System.out.println("[Mock] Simulando envio: " + message);
    }
}

// ===== Serviço que usa os produtos =====

class OrderServiceLegacy {
    private final DatabaseConnection db;
    private final NotificationService notifier;
    
    public OrderServiceLegacy(DatabaseConnection db, NotificationService notifier) {
        this.db = db;
        this.notifier = notifier;
    }
    
    public void placeOrder(String orderId) {
        db.execute("INSERT INTO orders VALUES ('" + orderId + "')");
        notifier.notify("Pedido " + orderId + " criado");
    }
}
