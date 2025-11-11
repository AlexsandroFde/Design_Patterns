package com.example.patterns.q1.repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteRepository implements Repository {

    private static final Logger LOGGER = Logger.getLogger(ClienteRepository.class.getName());
    private final ClienteDAO_DM_REPO dao;

    public ClienteRepository() {
        this(new ClienteDAO_DM_REPO(new ClienteDataMapper_REPO()));
    }

    public ClienteRepository(ClienteDAO_DM_REPO dao) {
        this.dao = dao;
    }

    @Override
    public synchronized List<Cliente_DM_REPO> list() {
        try {
            return dao.listar();
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao listar clientes no repository", ex);
            return List.of();
        }
    }

    @Override
    public synchronized boolean save(Cliente_DM_REPO cliente) {
        if (cliente == null) {
            LOGGER.warning("Tentativa de salvar cliente nulo");
            return false;
        }
        try {
            if (cliente.getId() == null) {
                return dao.inserir(cliente);
            } else {
                return dao.alterar(cliente);
            }
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar cliente no repository", ex);
            return false;
        }
    }

    @Override
    public synchronized boolean remove(Integer id) {
        if (id == null) {
            LOGGER.warning("Tentativa de remover cliente com id nulo");
            return false;
        }
        try {
            return dao.remover(id);
        } catch (RuntimeException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao remover cliente no repository", ex);
            return false;
        }
    }
}
