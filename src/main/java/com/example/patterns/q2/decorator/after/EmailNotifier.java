package com.example.patterns.q2.decorator.after;

/**
 * Componente concreto: envio por e-mail.
 */
public class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("[Email] " + message);
    }
}
