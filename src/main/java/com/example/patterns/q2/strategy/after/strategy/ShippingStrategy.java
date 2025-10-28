package com.example.patterns.q2.strategy.after.strategy;

/**
 * Questão 2 — Parte 1 (DEPOIS): Strategy
 * Interface de estratégia para cálculo de frete.
 */
public interface ShippingStrategy {
    double calculate(double orderTotal);
}
