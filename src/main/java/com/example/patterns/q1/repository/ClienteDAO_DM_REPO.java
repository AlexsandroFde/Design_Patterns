package com.example.patterns.q1.repository;

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

public class ClienteDAO_DM_REPO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO_DM_REPO.class.getName());
    private final Path filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ClienteDataMapper_REPO jsonMapper;

    public ClienteDAO_DM_REPO(ClienteDataMapper_REPO jsonMapper) {
        this.jsonMapper = jsonMapper;
        this.filePath = Paths.get("src/main/resources/data/clientes_repo.json");
        try {
            if (Files.notExists(filePath)) {
                Files.createDirectories(filePath.getParent());
                mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(),
                        new ArrayList<Map<String, Object>>());
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao inicializar arquivo JSON do DAO", ex);
        }
    }

    private List<Map<String, Object>> readRaw() throws IOException {
        if (Files.size(filePath) == 0) {
            return new ArrayList<>();
        }
        byte[] bytes = Files.readAllBytes(filePath);
        return mapper.readValue(bytes, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    private void writeRaw(List<Map<String, Object>> raw) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), raw);
    }

    public List<Cliente_DM_REPO> listar() {
        try {
            List<Map<String, Object>> raw = readRaw();
            return raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    public boolean inserir(Cliente_DM_REPO cliente) {
        try {
            List<Map<String, Object>> raw = readRaw();
            List<Cliente_DM_REPO> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

            Optional<Integer> maxId = domain
                    .stream()
                    .map(Cliente_DM_REPO::getId)
                    .filter(i -> i != null)
                    .max(Comparator.naturalOrder());

            int nextId = maxId.map(i -> i + 1).orElse(1);

            cliente.setId(nextId);
            domain.add(cliente);

            List<Map<String, Object>> toWrite = domain.stream().map(jsonMapper::toMap).collect(Collectors.toList());
            writeRaw(toWrite);
            return true;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Cliente_DM_REPO cliente) {
        if (cliente.getId() == null)
            return false;
        try {
            List<Map<String, Object>> raw = readRaw();
            List<Cliente_DM_REPO> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

            boolean found = false;
            for (int i = 0; i < domain.size(); i++) {
                if (cliente.getId().equals(domain.get(i).getId())) {
                    domain.set(i, cliente);
                    found = true;
                    break;
                }
            }

            if (found) {
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
        try {
            List<Map<String, Object>> raw = readRaw();
            List<Cliente_DM_REPO> domain = raw.stream().map(jsonMapper::fromMap).collect(Collectors.toList());

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
