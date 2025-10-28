package com.example.patterns.q3.di.after;

import java.util.function.Supplier;

/**
 * Wiring manual (sem framework) simulando um Container de DI.
 * Em frameworks como Spring/Guice, isso é automatizado com @Bean/@Inject, etc.
 */
public class AppConfig {
    public Logger logger() { return new ConsoleLogger(); }
    public Database database() { return new InMemoryDatabase(); }

    public Supplier<SalesReport> salesReportSupplier() {
        return () -> new SalesReport("Relatório de Vendas");
    }

    public OrderService orderService() {
        return new OrderService(logger(), database(), salesReportSupplier());
    }
}
