package com.example.patterns.q3.di.before;

/**
 * Questão 3 — Antes: uso de Singleton para acesso global a "banco de dados".
 * Problemas: estado global, difícil de testar, acoplamento forte.
 */
public class SingletonDatabase {
    private static final SingletonDatabase INSTANCE = new SingletonDatabase();

    // Observação: construtor protegido para permitir o "fake" no exemplo (quebra o encapsulamento típico do Singleton)
    protected SingletonDatabase() { }

    public static SingletonDatabase getInstance() {
        return INSTANCE;
    }

    public void save(String data) {
        System.out.println("[SingletonDB] salvando: " + data);
    }
}
