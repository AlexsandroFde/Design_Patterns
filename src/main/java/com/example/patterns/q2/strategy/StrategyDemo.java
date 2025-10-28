package com.example.patterns.q2.strategy;

import com.example.patterns.q2.strategy.before.FreightCalculatorBefore;
import com.example.patterns.q2.strategy.after.concrete_strategy.ExpressShipping;
import com.example.patterns.q2.strategy.after.concrete_strategy.PremiumShipping;
import com.example.patterns.q2.strategy.after.concrete_strategy.StandardShipping;
import com.example.patterns.q2.strategy.after.context.ShippingCalculatorContext;

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
        double v3 = before.calculate(orderTotal, "INTERNACIONAL", "PREMIUM");
        System.out.println("[ANTES] STANDARD = " + v1);
        System.out.println("[ANTES] EXPRESS  = " + v2);
        System.out.println("[ANTES] PREMIUM = " + v3);

        // DEPOIS (Strategy)
        ShippingCalculatorContext std = new ShippingCalculatorContext(new StandardShipping(false));
        ShippingCalculatorContext exp = new ShippingCalculatorContext(new ExpressShipping(false));
        ShippingCalculatorContext intl = new ShippingCalculatorContext(new PremiumShipping(true));
        System.out.println("[DEPOIS] STANDARD = " + std.calculate(orderTotal));
        System.out.println("[DEPOIS] EXPRESS = " + exp.calculate(orderTotal));
        System.out.println("[DEPOIS] PREMIUM = " + intl.calculate(orderTotal));
    }
}
