package com.equipe19;

import io.javalin.Javalin;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.time.Instant;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Configuração do Banco de Dados usando Variáveis de Ambiente
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(System.getenv("DB_URL"));
        cfg.setUsername(System.getenv("DB_USER"));
        cfg.setPassword(System.getenv("DB_PASSWORD"));
        cfg.setMaximumPoolSize(5); // Obrigatório limite de 5 conexões

        Javalin app = Javalin.create();

        // Rota de teste obrigatória exigida pelo portal
        app.get("/ping", ctx -> {
            ctx.json(Map.of(
                    "status", "ok",
                    "service", "eq19",
                    "timestamp", Instant.now().toString()
            ));
        });

        // Rota inicial padrão
        app.get("/", ctx -> ctx.result("Sistema de Cadastramento - Equipe 19"));

        // Iniciar obrigatoriamente na porta 8080
        app.start(8080);
    }
}