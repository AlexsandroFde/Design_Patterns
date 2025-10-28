package com.example.patterns.q3.di.after;

/**
 * Questão 3 — DEPOIS: Injeção de Dependência substituindo padrões criacionais.
 */
public class DIDemo {
    public static void main(String[] args) {
        System.out.println("Q3 - DEPOIS (DI)\n");

        // Wiring manual (simulando o que um container faria)
        AppConfig cfg = new AppConfig();
        OrderService service = cfg.orderService();

        service.place("010");
        service.generateReport();

        // Vantagem: fácil trocar dependências (ex.: logger falso para testes)
        OrderService testService = new OrderService(msg -> System.out.println("[FAKE-LOG] " + msg),
                data -> System.out.println("[FAKE-DB] " + data),
                () -> new SalesReport("Relatório (teste)"));
        testService.place("011");
        testService.generateReport();
    }
}
