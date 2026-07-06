package com.equipe19.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {

    private Long id;
    private Long clienteId;
    private Long servicoId;
    private LocalDate data;
    private LocalTime hora;
    private String status;
    private String observacao;

    public Agendamento() {
    }

    public Agendamento(Long id, Long clienteId, Long servicoId, LocalDate data, LocalTime hora, String status, String observacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.servicoId = servicoId;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}