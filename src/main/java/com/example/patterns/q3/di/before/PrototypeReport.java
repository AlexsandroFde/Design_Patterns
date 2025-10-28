package com.example.patterns.q3.di.before;

/**
 * Antes: Prototype via clone() para duplicar objetos.
 * Problema: pouco prático e frágil; em geral preferimos criar novos objetos com construtores ou Providers.
 */
public class PrototypeReport implements Cloneable {
    private String title;

    public PrototypeReport(String title) { this.title = title; }

    public String getTitle() { return title; }

    @Override
    public PrototypeReport clone() {
        try {
            return (PrototypeReport) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
