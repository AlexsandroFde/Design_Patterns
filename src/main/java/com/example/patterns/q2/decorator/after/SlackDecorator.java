package com.example.patterns.q2.decorator.after;

/**
 * Decorator: adiciona envio para Slack ao Notifier.
 */
public class SlackDecorator implements NotifierInterface {
    private final NotifierInterface delegate;

    public SlackDecorator(NotifierInterface delegate) {
        this.delegate = delegate;
    }

    @Override
    public void send(String message) {
        delegate.send(message);
        System.out.println("[Slack] " + message);
    }
}
