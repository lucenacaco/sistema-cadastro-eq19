package com.equipe19.service;

import com.equipe19.model.Usuario;
import com.equipe19.repository.UsuarioRepository;

public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService() {
        repository = new UsuarioRepository();
    }

    public void cadastrar(Usuario usuario) {

        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha é obrigatória.");
        }

        if (repository.buscarPorEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        repository.salvar(usuario);
    }

    public boolean autenticar(String email, String senha) {

        Usuario usuario = repository.buscarPorEmail(email);

        if (usuario == null) {
            return false;
        }

        return usuario.getSenha().equals(senha);
    }

    public Usuario buscarPorEmail(String email) {
        return repository.buscarPorEmail(email);
    }
}