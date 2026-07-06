package com.equipe19.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConexaoBanco {

    private static final HikariDataSource dataSource;

    static {

        HikariConfig config = new HikariConfig();

        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || url.isBlank()) {
            url = "jdbc:postgresql://localhost:5432/sistema_agendamento";
        }

        if (user == null || user.isBlank()) {
            user = "postgres";
        }

        if (password == null || password.isBlank()) {
            password = "1234";
        }

        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        config.setMaximumPoolSize(5);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}