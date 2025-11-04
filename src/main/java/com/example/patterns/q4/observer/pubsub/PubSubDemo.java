package com.example.patterns.q4.observer.pubsub;

public class PubSubDemo {
    public static void main(String[] args) {
        Broker broker = new Broker();

        Subscriber subA = (topic, msg) -> System.out.println("[PubSub] A recebeu de '" + topic + "': " + msg);
        Subscriber subB = (topic, msg) -> System.out.println("[PubSub] B recebeu de '" + topic + "': " + msg);

        broker.subscribe("orders", subA);
        broker.subscribe("payments", subA);
        broker.subscribe("orders", subB);

        broker.publish("orders", "pedido criado #123");
        broker.publish("payments", "pagamento aprovado #123");
        broker.publish("orders", "pedido enviado #123");
    }
}
