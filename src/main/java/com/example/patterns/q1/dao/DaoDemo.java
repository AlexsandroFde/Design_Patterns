package com.example.patterns.q1.dao;

import java.util.List;

public class DaoDemo {

    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();

        System.out.println("Lista inicial:");
        dao.listar().forEach(System.out::println);

        Cliente novo = new Cliente(null, "Carlos Santos", "333.333.333-33", "(31) 97777-2222");
        boolean inserido = dao.inserir(novo);
        System.out.println("\nInserido? " + inserido + " (id atribuído: " + novo.getId() + ")");

        System.out.println("\nDepois de inserir:");
        dao.listar().forEach(System.out::println);

        List<Cliente> todos = dao.listar();
        if (!todos.isEmpty()) {
            Cliente primeiro = todos.get(0);
            primeiro.setNome(primeiro.getNome() + " (alterado)");
            boolean alterado = dao.alterar(primeiro);
            System.out.println("\nAlterado? " + alterado);
        }

        System.out.println("\nDepois de alterar:");
        dao.listar().forEach(System.out::println);

        if (novo.getId() != null) {
            boolean removido = dao.remover(novo.getId());
            System.out.println("\nRemovido id " + novo.getId() + "? " + removido);
        }

        System.out.println("\nDepois de remover:");
        dao.listar().forEach(System.out::println);
    }

}
