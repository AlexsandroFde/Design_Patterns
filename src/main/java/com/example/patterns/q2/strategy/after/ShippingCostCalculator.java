package com.example.patterns.q2.strategy.after;

/**
 * Contexto que usa Strategy para calcular frete.
 */
public class ShippingCostCalculator {
    private final ShippingStrategy strategy;

    public ShippingCostCalculator(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double orderTotal) {
        return strategy.calculate(orderTotal);
    }

    public String strategyName() {
        return strategy.name();
    }
}
