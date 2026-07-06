package com.equipe19;

import com.equipe19.config.ConexaoBanco;
import com.equipe19.controller.*;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.time.Instant;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ConexaoBanco.getDataSource();

        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;

            config.staticFiles.add(staticFilesConfig -> {
                staticFilesConfig.hostedPath = "/";
                staticFilesConfig.directory = "/public";
            });

            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(Thread.currentThread().getContextClassLoader());
            templateResolver.setPrefix("thymeleaf/");
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML");
            templateResolver.setCharacterEncoding("UTF-8");

            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);

            config.fileRenderer(new JavalinThymeleaf(templateEngine));
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

        app.get("/", ctx -> ctx.redirect("/clientes"));

        app.start(8080);
    }
}