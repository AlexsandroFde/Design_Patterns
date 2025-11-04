package com.example.patterns.q4.observer.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class FlowApiDemo {
    static class PrintSubscriber implements Flow.Subscriber<String> {
        private Flow.Subscription subscription;
        private final String name;

        PrintSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1); // solicita o primeiro item
        }

        @Override
        public void onNext(String item) {
            System.out.println("[Flow] " + name + " recebeu: " + item);
            subscription.request(1); // solicita o próximo item (controle de backpressure)
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.println("[Flow] Erro: " + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("[Flow] " + name + " completo");
        }
    }

    public static void main(String[] args) throws Exception {
        try (SubmissionPublisher<String> publisher = new SubmissionPublisher<>()) {
            PrintSubscriber a = new PrintSubscriber("A");
            PrintSubscriber b = new PrintSubscriber("B");

            publisher.subscribe(a);
            publisher.subscribe(b);

            publisher.submit("Primeiro evento");
            publisher.submit("Segundo evento");
            publisher.submit("Terceiro evento");

            publisher.close();
            // Aguarda um pouco para as mensagens processarem
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
