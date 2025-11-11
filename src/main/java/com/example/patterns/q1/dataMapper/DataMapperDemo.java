package com.example.patterns.q1.dataMapper;

import java.util.List;

public class DataMapperDemo {

    public static void main(String[] args) {
        ClienteDataMapper jsonMapper = new ClienteDataMapper();
        ClienteDAO_DM dao = new ClienteDAO_DM(jsonMapper);

        System.out.println("Data Mapper (DAO + Mapper) - lista inicial:");
        dao.listar().forEach(System.out::println);

        Cliente_DM novo = new Cliente_DM(null, "Luciana Martins", "66666666666", "61944445555");
        boolean inserido = dao.inserir(novo);
        System.out.println("\nInserido? " + inserido + " (id: " + novo.getId() + ")");

        System.out.println("\nDepois de inserir:");
        dao.listar().forEach(System.out::println);

        List<Cliente_DM> todos = dao.listar();
        if (!todos.isEmpty()) {
            Cliente_DM primeiro = todos.get(0);
            primeiro.setNome(primeiro.getNome() + " (dm alterado)");
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
