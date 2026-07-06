package com.equipe19.controller;

import com.equipe19.model.Servico;
import com.equipe19.service.ServicoService;
import io.javalin.Javalin;

import java.math.BigDecimal;
import java.util.List;

public class ServicoController {

    private final ServicoService service;

    public ServicoController(Javalin app) {

        service = new ServicoService();

        app.post("/servicos", ctx -> {

            Servico servico = new Servico();

            servico.setNome(ctx.formParam("nome"));
            servico.setValor(new BigDecimal(ctx.formParam("valor")));
            servico.setDuracao(Integer.parseInt(ctx.formParam("duracao")));

            service.cadastrar(servico);

            ctx.status(201);
            ctx.result("Serviço cadastrado com sucesso.");
        });

        app.get("/servicos", ctx -> {

            List<Servico> servicos = service.listar();
            ctx.json(servicos);
        });

        app.get("/servicos/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            Servico servico = service.buscarPorId(id);

            if (servico == null) {
                ctx.status(404).result("Serviço não encontrado.");
                return;
            }

            ctx.json(servico);
        });

        app.put("/servicos/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            Servico servico = new Servico();
            servico.setId(id);
            servico.setNome(ctx.formParam("nome"));
            servico.setValor(new BigDecimal(ctx.formParam("valor")));
            servico.setDuracao(Integer.parseInt(ctx.formParam("duracao")));

            service.atualizar(servico);

            ctx.result("Serviço atualizado com sucesso.");
        });

        app.delete("/servicos/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            service.excluir(id);

            ctx.result("Serviço excluído com sucesso.");
        });
    }
}