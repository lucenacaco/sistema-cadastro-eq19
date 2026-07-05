package com.equipe19.service;

import com.equipe19.model.Cliente;
import com.equipe19.repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService() {
        repository = new ClienteRepository();
    }

    public void cadastrar(Cliente cliente) {

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório.");
        }

        if (cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }

        repository.salvar(cliente);
    }

    public List<Cliente> listar() {
        return repository.listar();
    }

    public Cliente buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Cliente cliente) {

        if (cliente.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório.");
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório.");
        }

        if (cliente.getEndereco() == null || cliente.getEndereco().isBlank()) {
            throw new IllegalArgumentException("Endereço é obrigatório.");
        }

        repository.atualizar(cliente);
    }

    public void excluir(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório.");
        }

        repository.excluir(id);
    }
}