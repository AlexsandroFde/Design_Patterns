package com.example.patterns.q3.di.after;

import java.util.function.Supplier;

/**
 * Serviço de domínio dependendo de abstrações (Logger, Database) e de um Supplier para objetos de vida curta.
 * Mostra como DI substitui padrões criacionais: nada de new/Singleton/Factory/Prototype aqui.
 */
public class OrderService {
    private final Logger logger;
    private final Database database;
    private final Supplier<SalesReport> reportSupplier; // similar a "prototype" scope

    public OrderService(Logger logger, Database database, Supplier<SalesReport> reportSupplier) {
        this.logger = logger;
        this.database = database;
        this.reportSupplier = reportSupplier;
    }

    public void place(String orderId) {
        logger.info("Colocando pedido " + orderId);
        String sql = "INSERT INTO orders (id) VALUES ('" + orderId + "')";
        database.executeSQL(sql);
    }

    public void generateReport() {
        SalesReport report = reportSupplier.get(); // novo objeto sob demanda
        logger.info("Gerando relatório: " + report.getTitle());
    }
}
