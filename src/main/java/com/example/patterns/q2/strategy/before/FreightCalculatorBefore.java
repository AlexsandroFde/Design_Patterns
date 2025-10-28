package com.example.patterns.q2.strategy.before;

/**
 * Questão 2 — Parte 1: Replace Conditional with Polymorphism → Strategy (ANTES)
 * Demonstra código com muitos if/else para cálculo de frete.
 * No pacote "after" mostramos a versão refatorada usando o padrão Strategy.
 */
public class FreightCalculatorBefore {
    public double calculate(double orderTotal, String destination, String service) {
        // Código legado cheio de condicionais
        double base;
        if ("STANDARD".equalsIgnoreCase(service)) {
            base = 10.0;
            if ("INTERNACIONAL".equalsIgnoreCase(destination)) {
                base += 25.0;
            }
            if (orderTotal > 200.0) {
                base *= 0.8; // desconto
            }
            return base;
        } else if ("EXPRESS".equalsIgnoreCase(service)) {
            base = 20.0;
            if ("INTERNACIONAL".equalsIgnoreCase(destination)) {
                base += 40.0;
            }
            if (orderTotal > 200.0) {
                base *= 0.9; // desconto
            }
            return base;
        } else if ("INTERNACIONAL".equalsIgnoreCase(service)) {
            base = 50.0;
            if (orderTotal > 500.0) {
                base -= 5.0; // promoção
            }
            return base;
        }
        throw new IllegalArgumentException("Serviço de frete desconhecido: " + service);
    }
}
