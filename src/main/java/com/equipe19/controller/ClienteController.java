package com.equipe19.controller;

import com.equipe19.model.Cliente;
import com.equipe19.service.ClienteService;
import com.equipe19.validator.ClienteValidator;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteController {

    private final ClienteService service;

    public ClienteController(Javalin app) {
        this.service = new ClienteService();

        app.get("/clientes", ctx -> {
            List<Cliente> clientes = service.listar();
            for (Cliente c : clientes) {
                c.setCpf(formatarCpf(c.getCpf()));
                c.setTelefone(formatarTelefone(c.getTelefone()));
            }
            Map<String, Object> model = new HashMap<>();
            model.put("clientes", clientes);
            ctx.render("lista-clientes.html", model);
        });

        app.get("/clientes/novo", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("cliente", new Cliente());
            ctx.render("form-cliente.html", model);
        });

        app.post("/clientes/salvar", ctx -> {
            Cliente cliente = new Cliente();
            cliente.setNome(ctx.formParam("nome"));
            cliente.setCpf(ctx.formParam("cpf") != null ? ctx.formParam("cpf").replaceAll("\\D", "") : "");
            cliente.setTelefone(ctx.formParam("telefone") != null ? ctx.formParam("telefone").replaceAll("\\D", "") : "");
            cliente.setEndereco(ctx.formParam("endereco"));

            Map<String, String> erros = ClienteValidator.validar(cliente);

            if (!erros.isEmpty()) {
                cliente.setEndereco(""); // Limpa o endereço para forçar buscar o CEP novamente
                Map<String, Object> model = new HashMap<>();
                model.put("cliente", cliente);
                model.put("erros", erros);
                ctx.render("form-cliente.html", model);
                return;
            }

            List<Cliente> atuais = service.listar();
            for (Cliente c : atuais) {
                if (c.getCpf().replaceAll("\\D", "").equals(cliente.getCpf())) {
                    if (c.getNome().equalsIgnoreCase(cliente.getNome())) {
                        ctx.redirect("/clientes");
                        return;
                    } else {
                        cliente.setEndereco(""); // Limpa o endereço antes de mostrar o pop-up
                        Map<String, Object> model = new HashMap<>();
                        model.put("cliente", cliente);
                        model.put("alertaErro", "Ocorreu algum problema, verifique o CPF ou o número digitado.");
                        ctx.render("form-cliente.html", model);
                        return;
                    }
                }
            }

            try {
                service.cadastrar(cliente);
                ctx.redirect("/clientes");
            } catch (Exception e) {
                cliente.setEndereco(""); // Limpa em caso de erro no banco
                Map<String, Object> model = new HashMap<>();
                model.put("cliente", cliente);
                model.put("alertaErro", "Ocorreu algum problema, verifique o CPF ou o número digitado.");
                ctx.render("form-cliente.html", model);
            }
        });

        app.get("/clientes/editar/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Cliente cliente = service.buscarPorId(id);
            Map<String, Object> model = new HashMap<>();
            model.put("cliente", cliente);
            ctx.render("form-cliente.html", model);
        });

        app.post("/clientes/atualizar/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));

            Cliente cliente = new Cliente();
            cliente.setId(id);
            cliente.setNome(ctx.formParam("nome"));
            cliente.setCpf(ctx.formParam("cpf") != null ? ctx.formParam("cpf").replaceAll("\\D", "") : "");
            cliente.setTelefone(ctx.formParam("telefone") != null ? ctx.formParam("telefone").replaceAll("\\D", "") : "");
            cliente.setEndereco(ctx.formParam("endereco"));

            Map<String, String> erros = ClienteValidator.validar(cliente);

            if (!erros.isEmpty()) {
                cliente.setEndereco(""); // Limpa o endereço se a validação falhar
                Map<String, Object> model = new HashMap<>();
                model.put("cliente", cliente);
                model.put("erros", erros);
                ctx.render("form-cliente.html", model);
                return;
            }

            try {
                service.atualizar(cliente);
                ctx.redirect("/clientes");
            } catch (Exception e) {
                cliente.setEndereco(""); // Limpa em caso de erro no banco
                Map<String, Object> model = new HashMap<>();
                model.put("cliente", cliente);
                model.put("alertaErro", "Ocorreu algum problema, verifique o CPF ou o número digitado.");
                ctx.render("form-cliente.html", model);
            }
        });

        app.post("/clientes/deletar/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            service.excluir(id);
            ctx.redirect("/clientes");
        });
    }

    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) return cpf;
        String c = cpf.replaceAll("\\D", "");
        return c.substring(0, 3) + "." + c.substring(3, 6) + "." + c.substring(6, 9) + "-" + c.substring(9);
    }

    private String formatarTelefone(String tel) {
        if (tel == null) return tel;
        String t = tel.replaceAll("\\D", "");
        if (t.length() == 11) {
            return "(" + t.substring(0, 2) + ") " + t.substring(2, 7) + "-" + t.substring(7);
        } else if (t.length() == 10) {
            return "(" + t.substring(0, 2) + ") " + t.substring(2, 6) + "-" + t.substring(6);
        }
        return tel;
    }
}