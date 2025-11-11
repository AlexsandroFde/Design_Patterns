package com.example.patterns.q1.dataMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClienteDAO_DM {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO_DM.class.getName());
    private final Path filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ClienteDataMapper jsonMapper;

    public ClienteDAO_DM(ClienteDataMapper jsonMapper) {
        // Inicializa o mapeador de dados entre JSON e objeto de domínio
        this.jsonMapper = jsonMapper;

        // Inicializa o caminho do arquivo JSON para armazenamento dos clientes
        // (simulando um banco de dados simples baseado em arquivo)
        this.filePath = Paths.get("src/main/resources/data/clientes_dm.json");
    }

    private List<Map<String, Object>> readRaw() throws IOException {
        // Lê todos os dados brutos do arquivo JSON
        byte[] bytes = Files.readAllBytes(filePath);
        return mapper.readValue(bytes, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    private void writeRaw(List<Map<String, Object>> raw) throws IOException {
        // Escreve os dados brutos no arquivo JSON (seja para inserir, alterar ou remover)
        mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), raw);
    }

    public List<Cliente_DM> listar() {
        // Lista todos os clientes
        try {
            List<Map<String, Object>> raw = readRaw();
            // Converte os dados brutos para objetos de domínio usando o mapeador
            return raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public boolean inserir(Cliente_DM cliente) {
        // Insere um novo cliente
        try {
            List<Map<String, Object>> raw = readRaw();
            // Converte os dados brutos para objetos de domínio usando o mapeador
            List<Cliente_DM> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

            // Calcula o próximo ID disponível
            Optional<Integer> maxId = domain
                    .stream()
                    .map(Cliente_DM::getId)
                    .filter(i -> i != null)
                    .max(Comparator.naturalOrder());
            int nextId = maxId.map(i -> i + 1).orElse(1);

            // Define o ID do novo cliente e adiciona à lista
            cliente.setId(nextId);
            domain.add(cliente);

            // Converte os objetos de domínio de volta para dados brutos usando o mapeador
            List<Map<String, Object>> toWrite = domain.stream().map(jsonMapper::toMap).collect(Collectors.toList());

            writeRaw(toWrite);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente_DM cliente) {
        // Altera um cliente existente
        if (cliente.getId() == null)
            return false;
        try {
            List<Map<String, Object>> raw = readRaw();
            // Converte os dados brutos para objetos de domínio usando o mapeador
            List<Cliente_DM> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

            // Procura o cliente pelo ID e atualiza seus dados
            boolean found = false;
            for (int i = 0; i < domain.size(); i++) {
                if (cliente.getId().equals(domain.get(i).getId())) {
                    domain.set(i, cliente);
                    found = true;
                    break;
                }
            }

            // Se encontrou o cliente, escreve a lista atualizada no arquivo JSON
            if (found) {
                // Converte os objetos de domínio de volta para dados brutos usando o mapeador
                List<Map<String, Object>> toWrite = domain.stream().map(jsonMapper::toMap).collect(Collectors.toList());

                writeRaw(toWrite);
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
            List<Map<String, Object>> raw = readRaw();
            List<Cliente_DM> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

            boolean removed = domain.removeIf(c -> id.equals(c.getId()));
            if (removed) {
                List<Map<String, Object>> toWrite = domain.stream().map(jsonMapper::toMap).collect(Collectors.toList());
                writeRaw(toWrite);
            }
            return removed;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
