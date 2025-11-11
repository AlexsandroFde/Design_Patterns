package com.example.patterns.q4.observer.classic;

public class ConcreteObserver implements Observer<String> {
    private final String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String event) {
        System.out.println("[Classic] " + name + " recebeu: " + event);
    }
}
