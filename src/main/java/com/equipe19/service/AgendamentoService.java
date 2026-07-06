package com.equipe19.service;

import com.equipe19.model.Agendamento;
import com.equipe19.repository.AgendamentoRepository;

import java.util.List;

public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService() {
        repository = new AgendamentoRepository();
    }

    public void cadastrar(Agendamento agendamento) {

        if (agendamento.getClienteId() == null ||
                agendamento.getServicoId() == null ||
                agendamento.getData() == null ||
                agendamento.getHora() == null ||
                agendamento.getStatus() == null ||
                agendamento.getStatus().isBlank()) {

            throw new IllegalArgumentException("Dados inválidos.");
        }

        repository.salvar(agendamento);
    }

    public List<Agendamento> listar() {
        return repository.listar();
    }

    public Agendamento buscarPorId(Long id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Agendamento agendamento) {
        repository.atualizar(agendamento);
    }

    public void excluir(Long id) {
        repository.excluir(id);
    }
}