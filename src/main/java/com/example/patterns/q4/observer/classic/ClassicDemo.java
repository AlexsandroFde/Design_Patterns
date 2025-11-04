package com.example.patterns.q4.observer.classic;

public class ClassicDemo {
    public static void main(String[] args) throws Exception {
        ConcreteSubject subject = new ConcreteSubject();
        subject.register(new ConcreteObserver("A"));
        subject.register(new ConcreteObserver("B"));

        subject.changeState("Primeiro evento");
        subject.changeState("Segundo evento");
    }
}
