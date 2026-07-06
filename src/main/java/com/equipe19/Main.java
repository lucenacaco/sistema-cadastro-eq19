package com.equipe19;

import com.equipe19.config.ConexaoBanco;
import com.equipe19.controller.AgendamentoController;
import com.equipe19.controller.ClienteController;
import com.equipe19.controller.ServicoController;
import com.equipe19.controller.UsuarioController;
import io.javalin.Javalin;

import java.time.Instant;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ConexaoBanco.getDataSource();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });

        new ClienteController(app);
        new ServicoController(app);
        new AgendamentoController(app);
        new UsuarioController(app);

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