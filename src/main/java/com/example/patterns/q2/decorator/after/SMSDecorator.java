package com.example.patterns.q2.decorator.after;

/**
 * Decorator: adiciona envio por SMS ao Notifier.
 */
public class SMSDecorator implements Notifier {
    private final Notifier delegate;

    public SMSDecorator(Notifier delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String message) {
        delegate.send(message);
        System.out.println("[SMS] " + message);
    }
}
