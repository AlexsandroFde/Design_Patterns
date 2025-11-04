package com.example.patterns.q4.observer.pubsub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker {
    private final Map<String, List<Subscriber>> subscribersByTopic = new HashMap<>();

    public void subscribe(String topic, Subscriber subscriber) {
        subscribersByTopic.computeIfAbsent(topic, t -> new ArrayList<>()).add(subscriber);
    }

    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> list = subscribersByTopic.get(topic);
        if (list != null) list.remove(subscriber);
    }

    public void publish(String topic, String message) {
        List<Subscriber> list = subscribersByTopic.get(topic);
        if (list == null) return;
        for (Subscriber s : list) {
            s.onMessage(topic, message);
        }
    }
}
