package com.example.patterns.q3.di.after;

public class ConsoleLogger implements Logger {
    @Override
    public void info(String msg) { System.out.println("[LOG] " + msg); }
}
