package com.example.patterns.q2.strategy.after.context;

import com.example.patterns.q2.strategy.after.strategy.ShippingStrategy;

/**
 * Contexto que usa Strategy para calcular frete.
 */
public class ShippingCalculatorContext {
    private final ShippingStrategy strategy;

    public ShippingCalculatorContext(ShippingStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double orderTotal) {
        return strategy.calculate(orderTotal);
    }
}
