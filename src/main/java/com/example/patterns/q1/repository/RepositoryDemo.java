package com.example.patterns.q1.repository;

import java.util.List;

public class RepositoryDemo {

    public static void main(String[] args) {
        ClienteRepository repository = new ClienteRepository();

        System.out.println("Lista inicial de clientes:");
        repository.list().forEach(System.out::println);

        Cliente_DM_REPO novo = new Cliente_DM_REPO(null, "Ana Paula", "444.444.444-44", "(41) 98888-7777");
        boolean inserido = repository.save(novo);
        System.out.println("\nInserido? " + inserido + " (id: " + novo.getId() + ")");

        System.out.println("\nDepois de inserir:");
        repository.list().forEach(System.out::println);

        List<Cliente_DM_REPO> todos = repository.list();
        if (!todos.isEmpty()) {
            Cliente_DM_REPO primeiro = todos.get(0);
            primeiro.setNome(primeiro.getNome() + " (repo alterado)");
            boolean alterado = repository.save(primeiro);
            System.out.println("\nAlterado? " + alterado);
        }

        System.out.println("\nDepois de alterar:");
        repository.list().forEach(System.out::println);

        if (novo.getId() != null) {
            boolean removido = repository.remove(novo.getId());
            System.out.println("\nRemovido id " + novo.getId() + "? " + removido);
        }

        System.out.println("\nDepois de remover:");
        repository.list().forEach(System.out::println);
    }
}
