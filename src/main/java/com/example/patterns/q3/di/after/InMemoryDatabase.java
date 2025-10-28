package com.example.patterns.q3.di.after;

public class InMemoryDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("[InMemoryDB] " + data);
    }
}
