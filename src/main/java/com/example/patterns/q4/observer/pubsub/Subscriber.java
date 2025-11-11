package com.example.patterns.q4.observer.pubsub;

public interface Subscriber {
    void onMessage(String topic, String message);
}
