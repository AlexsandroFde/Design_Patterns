package com.example.patterns.q3.di.after;

import java.util.function.Supplier;

/**
 * Questão 3 — DEPOIS: Injeção de Dependência substituindo padrões criacionais.
 */
public class DIDemo {
    public static void main(String[] args) {
        System.out.println("Q3 - DEPOIS (Dependency Injection)\n");

        // ==== 1) Substitui SINGLETON ====
        System.out.println("1) DI substitui SINGLETON - sem estado global");
        // Antes: SingletonDatabase.getInstance() (sempre o mesmo)
        // Depois: cada serviço recebe sua própria instância (podem ser bancos diferentes!)
        Database db1 = new MySQLDatabase();
        Database db2 = new InMemoryDatabase();
        
        UserService service1 = new UserService(db1); // injeta MySQL
        UserService service2 = new UserService(db2); // injeta InMemory
        
        service1.saveUser("Jonas");
        service2.saveUser("Maria");
        System.out.println("✓ Dois bancos diferentes, instâncias independentes (sem estado global)\n");

        // ==== 2) Substitui ABSTRACT FACTORY ====
        System.out.println("2) DI substitui ABSTRACT FACTORY - sem verbosidade");
        
        // PRODUÇÃO: usa MySQL
        Database prodDb = new MySQLDatabase();
        UserService prodService = new UserService(prodDb);
        prodService.saveUser("Cliente Produção");
        
        System.out.println();
        
        // TESTE: usa InMemory (sem criar factory!)
        Database testDb = new InMemoryDatabase();
        UserService testService = new UserService(testDb);
        testService.saveUser("Cliente Teste");
        System.out.println("✓ Troca implementação em 1 linha (vs 9 classes do Abstract Factory)\n");

        // ==== 3) Substitui PROTOTYPE ====
        System.out.println("3) DI substitui PROTOTYPE - sem clone()");
        
        // ANTES: PrototypeReport.clone() (frágil, shallow copy)
        // DEPOIS: Supplier<T> gera novas instâncias sob demanda
        Supplier<SalesReport> reportFactory = () -> new SalesReport("Relatório Mensal");
        SalesReport report1 = reportFactory.get();
        SalesReport report2 = reportFactory.get();
        System.out.println("Report 1: " + report1.getTitle());
        System.out.println("Report 2: " + report2.getTitle());
        System.out.println("✓ Objetos independentes via Supplier (sem clone())\n");

        // ==== VANTAGENS PRINCIPAIS DA DI ====
        System.out.println("=== VANTAGENS DA DI ===");
        System.out.println("✓ Testabilidade: injeta mocks/fakes facilmente");
        System.out.println("✓ Desacoplamento: UserService depende de Database (interface), não MySQL");
        System.out.println("✓ Simplicidade: sem explosão de factories/classes");
        System.out.println("✓ Flexibilidade: troca MySQL por InMemory sem modificar UserService");
        System.out.println("✓ Abstração SQL: Database.executeSQL() esconde detalhes de implementação");
    }
}
