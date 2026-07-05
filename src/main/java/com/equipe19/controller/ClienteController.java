package com.equipe19.controller;

import com.equipe19.model.Cliente;
import com.equipe19.service.ClienteService;
import io.javalin.Javalin;

import java.util.List;

public class ClienteController {

    private final ClienteService service;

    public ClienteController(Javalin app) {

        this.service = new ClienteService();

        app.post("/clientes", ctx -> {

            Cliente cliente = new Cliente();
            cliente.setNome(ctx.formParam("nome"));
            cliente.setCpf(ctx.formParam("cpf"));
            cliente.setTelefone(ctx.formParam("telefone"));
            cliente.setEndereco(ctx.formParam("endereco"));

            service.cadastrar(cliente);

            ctx.status(201);
            ctx.result("Cliente cadastrado com sucesso.");
        });

        app.get("/clientes", ctx -> {

            List<Cliente> clientes = service.listar();
            ctx.json(clientes);
        });

        app.get("/clientes/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            Cliente cliente = service.buscarPorId(id);

            if (cliente == null) {
                ctx.status(404).result("Cliente não encontrado");
                return;
            }

            ctx.json(cliente);
        });

        app.put("/clientes/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            Cliente cliente = new Cliente();
            cliente.setId(id);
            cliente.setNome(ctx.formParam("nome"));
            cliente.setCpf(ctx.formParam("cpf"));
            cliente.setTelefone(ctx.formParam("telefone"));
            cliente.setEndereco(ctx.formParam("endereco"));

            service.atualizar(cliente);

            ctx.result("Cliente atualizado com sucesso.");
        });

        app.delete("/clientes/{id}", ctx -> {

            Long id = Long.parseLong(ctx.pathParam("id"));

            service.excluir(id);

            ctx.result("Cliente excluído com sucesso.");
        });
    }
}