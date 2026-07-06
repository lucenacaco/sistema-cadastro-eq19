package com.equipe19.controller;

import com.equipe19.model.Usuario;
import com.equipe19.service.UsuarioService;
import io.javalin.Javalin;

public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(Javalin app) {

        service = new UsuarioService();

        app.post("/usuarios", ctx -> {

            Usuario usuario = new Usuario();

            usuario.setNome(ctx.formParam("nome"));
            usuario.setEmail(ctx.formParam("email"));
            usuario.setSenha(ctx.formParam("senha"));

            service.cadastrar(usuario);

            ctx.status(201);
            ctx.result("Usuário cadastrado com sucesso.");
        });

        app.post("/login", ctx -> {

            String email = ctx.formParam("email");
            String senha = ctx.formParam("senha");

            boolean autenticado = service.autenticar(email, senha);

            if (!autenticado) {
                ctx.status(401);
                ctx.result("E-mail ou senha inválidos.");
                return;
            }

            ctx.sessionAttribute("usuario", email);

            ctx.result("Login realizado com sucesso.");
        });

        app.get("/logout", ctx -> {

            ctx.req().getSession().invalidate();

            ctx.result("Logout realizado com sucesso.");
        });

        app.get("/usuario-logado", ctx -> {

            String usuario = ctx.sessionAttribute("usuario");

            if (usuario == null) {
                ctx.status(401);
                ctx.result("Nenhum usuário logado.");
                return;
            }

            ctx.result(usuario);
        });
    }
}