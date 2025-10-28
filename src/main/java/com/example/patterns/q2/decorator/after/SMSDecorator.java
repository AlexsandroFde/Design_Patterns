package com.example.patterns.q2.decorator.after;

/**
 * Decorator: adiciona envio por SMS ao Notifier.
 */
public class SMSDecorator implements NotifierInterface {
    private final NotifierInterface delegate;

    public SMSDecorator(NotifierInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String message) {
        delegate.send(message);
        System.out.println("[SMS] " + message);
    }
}
