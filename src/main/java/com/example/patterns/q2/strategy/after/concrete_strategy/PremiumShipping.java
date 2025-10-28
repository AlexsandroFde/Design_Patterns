package com.example.patterns.q2.strategy.after.concrete_strategy;

import com.example.patterns.q2.strategy.after.strategy.ShippingStrategy;

public class PremiumShipping implements ShippingStrategy {
    private final boolean international;

    public PremiumShipping(boolean international) {
        this.international = international;
    }

    @Override
    public double calculate(double orderTotal) {
        double base = 50.0 + (international ? 20.0 : 0.0);
        if (orderTotal > 500.0) base -= 5.0;
        return base;
    }
}
