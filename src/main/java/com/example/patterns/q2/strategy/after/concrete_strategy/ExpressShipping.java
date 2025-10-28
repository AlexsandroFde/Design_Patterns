package com.example.patterns.q2.strategy.after.concrete_strategy;

import com.example.patterns.q2.strategy.after.strategy.ShippingStrategy;

public class ExpressShipping implements ShippingStrategy {
    private final boolean international;

    public ExpressShipping(boolean international) {
        this.international = international;
    }

    @Override
    public double calculate(double orderTotal) {
        double base = 20.0 + (international ? 40.0 : 0.0);
        if (orderTotal > 200.0) base *= 0.9;
        return base;
    }
}
