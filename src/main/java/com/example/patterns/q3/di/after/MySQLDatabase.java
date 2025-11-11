package com.example.patterns.q3.di.after;

public class MySQLDatabase implements Database {
    @Override
    public void executeSQL(String sql) {
        System.out.println("[MySQL] Conectando ao servidor MySQL...");
        System.out.println("[MySQL] Executando: " + sql);
        System.out.println("[MySQL] ✓ Comando enviado ao MySQL");
    }
}
