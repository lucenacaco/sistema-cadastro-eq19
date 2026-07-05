package com.equipe19;

import com.equipe19.config.ConexaoBanco;
import io.javalin.Javalin;

import java.time.Instant;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ConexaoBanco.getDataSource();

        Javalin app = Javalin.create();

        app.get("/ping", ctx -> {
            ctx.json(Map.of(
                    "status", "ok",
                    "service", "eq19",
                    "timestamp", Instant.now().toString()
            ));
        });

        app.get("/", ctx -> ctx.result("Sistema de Agendamento - Equipe 19"));

        app.start(8080);
    }
}