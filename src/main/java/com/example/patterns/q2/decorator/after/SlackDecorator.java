package com.example.patterns.q2.decorator.after;

/**
 * Decorator: adiciona envio para Slack ao Notifier.
 */
public class SlackDecorator implements Notifier {
    private final Notifier delegate;

    public SlackDecorator(Notifier delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String message) {
        delegate.send(message);
        System.out.println("[Slack] " + message);
    }
}
