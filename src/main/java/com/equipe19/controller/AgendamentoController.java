package com.equipe19.controller;

import com.equipe19.model.Agendamento;
import com.equipe19.service.AgendamentoService;
import io.javalin.Javalin;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(Javalin app) {

        service = new AgendamentoService();

        app.post("/agendamentos", ctx -> {

            Agendamento agendamento = new Agendamento();

            agendamento.setClienteId(Long.parseLong(ctx.formParam("clienteId")));
            agendamento.setServicoId(Long.parseLong(ctx.formParam("servicoId")));
            agendamento.setData(LocalDate.parse(ctx.formParam("data")));
            agendamento.setHora(LocalTime.parse(ctx.formParam("hora")));
            agendamento.setStatus(ctx.formParam("status"));
            agendamento.setObservacao(ctx.formParam("observacao"));

            service.cadastrar(agendamento);

            ctx.status(201).result("Agendamento cadastrado.");
        });

        app.get("/agendamentos", ctx -> ctx.json(service.listar()));

        app.get("/agendamentos/{id}", ctx -> {

            Agendamento agendamento = service.buscarPorId(Long.parseLong(ctx.pathParam("id")));

            if (agendamento == null) {
                ctx.status(404);
                return;
            }

            ctx.json(agendamento);
        });

        app.delete("/agendamentos/{id}", ctx -> {

            service.excluir(Long.parseLong(ctx.pathParam("id")));

            ctx.result("Agendamento excluído.");
        });
    }
}