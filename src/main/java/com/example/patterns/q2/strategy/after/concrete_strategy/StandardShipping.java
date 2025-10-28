package com.example.patterns.q2.strategy.after.concrete_strategy;

import com.example.patterns.q2.strategy.after.strategy.ShippingStrategy;

public class StandardShipping implements ShippingStrategy {
    private final boolean international;

    public StandardShipping(boolean international) {
        this.international = international;
    }

    @Override
    public double calculate(double orderTotal) {
        double base = 10.0 + (international ? 25.0 : 0.0);
        if (orderTotal > 200.0) base *= 0.8;
        return base;
    }
}
