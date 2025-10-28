package com.example.patterns.q2.decorator.before;

/**
 * Questão 2 — Parte 2: Move Embellishment to Decorator (ANTES)
 * Classe com várias responsabilidades de "embelezamento" (enviar por canais extras)
 * misturadas na mesma classe.
 */
public class NotifierBefore {
    public void send(String message, boolean email, boolean sms, boolean slack) {
        if (email) {
            System.out.println("[Email] " + message);
        }
        if (sms) {
            // Lógica de SMS embutida aqui
            System.out.println("[SMS] " + message);
        }
        if (slack) {
            // Lógica de Slack embutida aqui
            System.out.println("[Slack] " + message);
        }
    }
}
