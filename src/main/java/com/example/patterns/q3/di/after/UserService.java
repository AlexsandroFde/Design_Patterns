package com.example.patterns.q3.di.after;

/**
 * Serviço que depende de Database (abstração).
 * Recebe a dependência via construtor (Dependency Injection).
 */
public class UserService {
    private final Database db;

    // Injeção de Dependência via construtor
    public UserService(Database db) {
        this.db = db;
    }

    public void saveUser(String username) {
        String sql = "INSERT INTO users (name) VALUES ('" + username + "')";
        db.executeSQL(sql);
    }
}
