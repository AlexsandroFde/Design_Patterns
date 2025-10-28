package com.example.patterns.q2.strategy.after;

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

    @Override
    public String name() { return international ? "STANDARD (INTL)" : "STANDARD"; }
}
