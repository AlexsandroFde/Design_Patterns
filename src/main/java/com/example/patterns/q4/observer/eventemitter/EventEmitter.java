package com.example.patterns.q4.observer.eventemitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Simulação do EventEmitter do Node.js em Java.
 * Inspirado em: Node.js EventEmitter, que é puro Observer Pattern.
 */
public class EventEmitter {
    private final Map<String, List<Consumer<String>>> listeners = new HashMap<>();

    /**
     * Registra um listener para um evento específico.
     * Equivalente a: emitter.on('event', callback)
     */
    public void on(String eventName, Consumer<String> listener) {
        listeners.putIfAbsent(eventName, new ArrayList<>());
        listeners.get(eventName).add(listener);
    }

    /**
     * Remove um listener específico.
     * Equivalente a: emitter.off('event', callback)
     */
    public void off(String eventName, Consumer<String> listener) {
        List<Consumer<String>> eventListeners = listeners.get(eventName);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    /**
     * Emite um evento para todos os listeners registrados.
     * Equivalente a: emitter.emit('event', data)
     */
    public void emit(String eventName, String data) {
        List<Consumer<String>> eventListeners = listeners.get(eventName);
        if (eventListeners != null) {
            // Cria cópia da lista para evitar ConcurrentModificationException
            // (listeners podem se remover durante iteração, ex: once())
            List<Consumer<String>> listenersCopy = new ArrayList<>(eventListeners);
            for (Consumer<String> listener : listenersCopy) {
                listener.accept(data);
            }
        }
    }

    /**
     * Registra listener que executa apenas UMA vez.
     * Equivalente a: emitter.once('event', callback)
     */
    public void once(String eventName, Consumer<String> listener) {
        Consumer<String> wrapper = new Consumer<String>() {
            @Override
            public void accept(String data) {
                listener.accept(data);
                off(eventName, this); // Remove após primeira execução
            }
        };
        on(eventName, wrapper);
    }
}
