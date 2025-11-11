package com.example.patterns.q4.observer.classic;

public class ClassicDemo {
    public static void main(String[] args) throws Exception {
        ConcreteSubject subject = new ConcreteSubject();
        
        // Cria os observers separadamente
        ConcreteObserver observerA = new ConcreteObserver("A");
        ConcreteObserver observerB = new ConcreteObserver("B");
        
        // Registra os observers no subject
        subject.register(observerA);
        subject.register(observerB);

        // Dispara eventos que notificam todos os observers
        subject.changeState("Primeiro evento");
        subject.changeState("Segundo evento");
    }
}
