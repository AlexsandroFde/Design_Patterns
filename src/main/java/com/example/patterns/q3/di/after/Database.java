package com.example.patterns.q3.di.after;

/**
 * Abstração de banco de dados.
 * Cada implementação decide como executar SQL.
 */
public interface Database {
    void executeSQL(String sql);
}
