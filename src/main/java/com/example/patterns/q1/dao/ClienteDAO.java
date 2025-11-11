package com.example.patterns.q1.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());
    private final Path filePath;
    private final ObjectMapper mapper = new ObjectMapper();

    public ClienteDAO() {
        // Inicializa o caminho do arquivo JSON para armazenamento dos clientes
        // (simulando um banco de dados simples baseado em arquivo)
        this.filePath = Paths.get("src/main/resources/data/clientes.json");
    }

    private List<Cliente> readAll() throws IOException {
        // Lê todos os clientes do arquivo JSON
        byte[] bytes = Files.readAllBytes(filePath);
        return mapper.readValue(bytes, new TypeReference<List<Cliente>>() {});
    }

    private void writeAll(List<Cliente> lista) throws IOException {
        // Escreve todos os clientes da lista (seja para inserir, alterar ou remover) no arquivo JSON
        mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), lista);
    }

    public List<Cliente> listar() {
        // Lista todos os clientes
        try {
            return readAll();
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public boolean inserir(Cliente cliente) {
        // Insere um novo cliente
        try {
            List<Cliente> lista = readAll();

            // Calcula o próximo ID disponível
            Optional<Integer> maxId = lista
                    .stream()
                    .map(Cliente::getId)
                    .filter(i -> i != null)
                    .max(Comparator.naturalOrder());
            int nextId = maxId.map(i -> i + 1).orElse(1);

            // Define o ID do novo cliente e adiciona à lista
            cliente.setId(nextId);
            lista.add(cliente);
            writeAll(lista);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente cliente) {
        // Altera um cliente existente
        if (cliente.getId() == null)
            return false;
        try {
            List<Cliente> lista = readAll();

            // Procura o cliente pelo ID e atualiza seus dados
            boolean found = false;
            for (int i = 0; i < lista.size(); i++) {
                if (cliente.getId().equals(lista.get(i).getId())) {
                    lista.set(i, cliente);
                    found = true;
                    break;
                }
            }
            
            // Se encontrou o cliente, escreve a lista atualizada no arquivo JSON
            if (found) {
                writeAll(lista);
            }
            return found;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Integer id) {
        // Remove um cliente pelo ID
        try {
            List<Cliente> lista = readAll();

            boolean removed = lista.removeIf(c -> id.equals(c.getId()));

            if (removed) {
                writeAll(lista);
            }
            return removed;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
