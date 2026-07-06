package com.equipe19.model;

import java.math.BigDecimal;

public class Servico {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer duracao;

    public Servico() {
    }

    public Servico(Long id, String nome, BigDecimal valor, Integer duracao) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.duracao = duracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }
}
