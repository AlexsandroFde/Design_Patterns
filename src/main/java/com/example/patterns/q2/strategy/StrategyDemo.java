package com.example.patterns.q2.strategy;

import com.example.patterns.q2.strategy.before.FreightCalculatorBefore;
import com.example.patterns.q2.strategy.after.*;

/**
 * Questão 2 — Parte 1: Demonstração do refactor para Strategy.
 */
public class StrategyDemo {
    public static void main(String[] args) {
        System.out.println("Q2 - Strategy: antes (if/else) vs depois (polimorfismo)\n");

        double orderTotal = 250.0;

        // ANTES
        FreightCalculatorBefore before = new FreightCalculatorBefore();
        double v1 = before.calculate(orderTotal, "NACIONAL", "STANDARD");
        double v2 = before.calculate(orderTotal, "NACIONAL", "EXPRESS");
        double v3 = before.calculate(orderTotal, "INTERNACIONAL", "INTERNACIONAL");
        System.out.println("[ANTES] STANDARD = " + v1);
        System.out.println("[ANTES] EXPRESS  = " + v2);
        System.out.println("[ANTES] INTERNACIONAL = " + v3);

        // DEPOIS (Strategy)
        ShippingCostCalculator std = new ShippingCostCalculator(new StandardShipping(false));
        ShippingCostCalculator exp = new ShippingCostCalculator(new ExpressShipping(false));
        ShippingCostCalculator intl = new ShippingCostCalculator(new InternationalShipping());
        System.out.println("[DEPOIS] " + std.strategyName() + " = " + std.calculate(orderTotal));
        System.out.println("[DEPOIS] " + exp.strategyName() + " = " + exp.calculate(orderTotal));
        System.out.println("[DEPOIS] " + intl.strategyName() + " = " + intl.calculate(orderTotal));
    }
}
