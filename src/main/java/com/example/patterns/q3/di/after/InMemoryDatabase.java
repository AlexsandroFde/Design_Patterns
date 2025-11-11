package com.example.patterns.q3.di.after;

public class InMemoryDatabase implements Database {
    @Override
    public void executeSQL(String sql) {
        System.out.println("[InMemoryDB] Executando: " + sql);
    }
}
