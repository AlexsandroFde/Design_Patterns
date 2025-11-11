package com.example.patterns.q4.observer.classic;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject<String> {
    private final List<Observer<String>> observers = new ArrayList<>();

    @Override
    public void register(Observer<String> observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer<String> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer<String> obs : observers) {
            obs.update(event);
        }
    }

    public void changeState(String newState) {
        notifyObservers(newState);
    }
}
