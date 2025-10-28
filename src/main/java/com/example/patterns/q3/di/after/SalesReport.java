package com.example.patterns.q3.di.after;

/**
 * No lugar de Prototype/clone(), apenas criamos um novo objeto quando precisamos.
 * Um DI Container moderno pode injetar Suppliers/Providers para escopo "prototype".
 */
public class SalesReport {
    private final String title;

    public SalesReport(String title) { this.title = title; }

    public String getTitle() { return title; }
}
