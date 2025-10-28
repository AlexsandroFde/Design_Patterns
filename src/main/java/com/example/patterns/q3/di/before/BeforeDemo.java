package com.example.patterns.q3.di.before;

/**
 * Questão 3 — Demonstração ANTES: uso de padrões criacionais criticados
 * (Singleton, Abstract Factory, Prototype) sem DI.
 */
public class BeforeDemo {
    public static void main(String[] args) {
        System.out.println("Q3 - ANTES (padrões criacionais clássicos)\n");

        // Singleton
        SingletonDatabase.getInstance().save("order=001");

        // Abstract Factory
        ServiceAbstractFactory prodFactory = new ServiceAbstractFactory.ProdFactory();
        prodFactory.createOrderService().place("002");

        // Prototype
        PrototypeReport r1 = new PrototypeReport("Relatório de Vendas");
        PrototypeReport r2 = r1.clone();
        System.out.println("Prototype cloned: " + r2.getTitle());
    }
}
