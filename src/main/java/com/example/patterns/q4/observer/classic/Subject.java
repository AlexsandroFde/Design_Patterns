package com.example.patterns.q4.observer.classic;

public interface Subject<T> {
    void register(Observer<T> observer);
    void unregister(Observer<T> observer);
    void notifyObservers(T event);
}

