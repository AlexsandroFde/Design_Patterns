package com.example.patterns.q4.observer.eventemitter;

/**
 * Questão 4 — EventEmitter (estilo Node.js)
 * 
 * Demonstra como Node.js implementa Observer Pattern de forma simples e direta.
 * EventEmitter é a base de praticamente toda a API do Node.js.
 */
public class EventEmitterDemo {
    public static void main(String[] args) {
        EventEmitter emitter = new EventEmitter();

        // Registra listeners (equivalente a emitter.on('data', callback))
        emitter.on("data", data -> 
            System.out.println("[EventEmitter] Listener 1 recebeu: " + data)
        );
        
        emitter.on("data", data -> 
            System.out.println("[EventEmitter] Listener 2 recebeu: " + data)
        );

        // Listener que executa apenas uma vez
        emitter.once("connection", data -> 
            System.out.println("[EventEmitter] Conectado (only once): " + data)
        );

        // Emite eventos (equivalente a emitter.emit('data', 'hello'))
        System.out.println("--- Emitindo 'data' ---");
        emitter.emit("data", "Primeiro evento");
        emitter.emit("data", "Segundo evento");

        System.out.println("\n--- Emitindo 'connection' duas vezes ---");
        emitter.emit("connection", "Client #1");
        emitter.emit("connection", "Client #2");  // Este não imprime (once)
    }
}
