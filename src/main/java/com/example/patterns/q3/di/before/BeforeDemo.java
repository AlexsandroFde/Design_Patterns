package com.example.patterns.q3.di.before;

/**
 * Questão 3 — Demonstração ANTES: uso de padrões criacionais criticados
 * (Singleton, Abstract Factory, Prototype) sem DI.
 */
public class BeforeDemo {
    public static void main(String[] args) {
        System.out.println("Q3 - ANTES (padrões criacionais clássicos)\n");

        // ==== 1) SINGLETON ====
        System.out.println("1) SINGLETON - acesso global, dificulta testes");
        SingletonDatabase.getInstance().save("order=001");
        SingletonDatabase.getInstance().save("order=002");
        // Problema: estado global, impossível trocar por mock facilmente
        
        System.out.println();

        // ==== 2) ABSTRACT FACTORY ====
        System.out.println("2) ABSTRACT FACTORY - verbosidade excessiva");
        
        // Para PRODUÇÃO: precisa criar factory específica
        ServiceAbstractFactory prodFactory = new ServiceAbstractFactory.ProductionFactory();
        DatabaseConnection prodDb = prodFactory.createDatabase();
        NotificationService prodNotifier = prodFactory.createNotifier();
        OrderServiceLegacy prodService = new OrderServiceLegacy(prodDb, prodNotifier);
        prodService.placeOrder("ORD-100");
        
        // Para TESTE: precisa OUTRA factory
        ServiceAbstractFactory testFactory = new ServiceAbstractFactory.TestFactory();
        DatabaseConnection testDb = testFactory.createDatabase();
        NotificationService testNotifier = testFactory.createNotifier();
        OrderServiceLegacy testService = new OrderServiceLegacy(testDb, testNotifier);
        testService.placeOrder("TEST-999");
        // Problema: 2 interfaces + 4 classes concretas só para criar objetos!
        
        System.out.println();

        // ==== 3) PROTOTYPE ====
        System.out.println("3) PROTOTYPE - clone() frágil e pouco prático");
        PrototypeReport r1 = new PrototypeReport("Relatório de Vendas");
        PrototypeReport r2 = r1.clone();
        System.out.println("Original: " + r1.getTitle());
        System.out.println("Clone: " + r2.getTitle());
        
    }
}
