package com.example.patterns.q2.decorator;

import com.example.patterns.q2.decorator.before.NotifierBefore;
import com.example.patterns.q2.decorator.after.*;

/**
 * Questão 2 — Parte 2: Demonstração do refactor para Decorator.
 */
public class DecoratorDemo {
    public static void main(String[] args) {
        System.out.println("Q2 - Decorator: antes (classe cheia de responsabilidades) vs depois (decorators)\n");

        // ANTES
        System.out.println("Antes:\n");
        NotifierBefore before = new NotifierBefore();
        before.send("Pedido 123 aprovado", 
            true, 
            true, 
            true
        );

        // DEPOIS
        System.out.println("Depois:\n");
        NotifierInterface notifier = new EmailNotifier();
        notifier = new SMSDecorator(notifier);
        notifier = new SlackDecorator(notifier);
        notifier.send("Pedido 123 aprovado");
    }
}
