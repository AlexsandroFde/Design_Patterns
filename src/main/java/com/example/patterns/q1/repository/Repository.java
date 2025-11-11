package com.example.patterns.q1.repository;

import java.util.List;

public interface Repository {
    List<Cliente_DM_REPO> list();

    boolean save(Cliente_DM_REPO cliente);

    boolean remove(Integer id);
}
