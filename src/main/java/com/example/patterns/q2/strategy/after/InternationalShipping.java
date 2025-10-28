package com.example.patterns.q2.strategy.after;

public class InternationalShipping implements ShippingStrategy {
    @Override
    public double calculate(double orderTotal) {
        double base = 50.0;
        if (orderTotal > 500.0) base -= 5.0;
        return base;
    }

    @Override
    public String name() { return "INTERNACIONAL"; }
}
