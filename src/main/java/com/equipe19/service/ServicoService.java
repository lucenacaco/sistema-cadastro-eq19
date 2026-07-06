package com.equipe19.service;

import com.equipe19.model.Servico;
import com.equipe19.repository.ServicoRepository;

import java.util.List;

public class ServicoService {

    private final ServicoRepository repository;

    public ServicoService() {
        repository = new ServicoRepository();
    }

    public void cadastrar(Servico servico) {

        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (servico.getValor() == null) {
            throw new IllegalArgumentException("Valor é obrigatório.");
        }

        if (servico.getDuracao() == null) {
            throw new IllegalArgumentException("Duração é obrigatória.");
        }

        repository.salvar(servico);
    }

    public List<Servico> listar() {
        return repository.listar();
    }

    public Servico buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Servico servico) {

        if (servico.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório.");
        }

        repository.atualizar(servico);
    }

    public void excluir(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório.");
        }

        repository.excluir(id);
    }
}
