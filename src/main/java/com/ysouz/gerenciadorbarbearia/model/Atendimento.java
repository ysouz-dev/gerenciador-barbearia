package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.enums.Servico;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Objects;

public class Atendimento {

    private Integer id;
    private List<Servico> servicosRealizados;
    private BigDecimal totalServico;
    private Pessoa cliente;

    public Atendimento(Pessoa cliente) {
        if (Objects.isNull(cliente)){
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        this.servicosRealizados = new ArrayList<Servico>();
        this.cliente = cliente;
        this.id = null;
        this.totalServico = BigDecimal.ZERO;
    }

    public Atendimento(Integer id, Pessoa cliente) {
        this.servicosRealizados = new ArrayList<>();
        this.id = id;
        this.cliente = cliente;
        this.totalServico = BigDecimal.ZERO;
    }

    public void adicionarServico(Servico servico) {
        Servico.isServico(servico);
        if (containsServico(this.servicosRealizados, servico)) {
            throw new IllegalArgumentException("Esse serviço já foi registrado nesse atendimento.");
        }

        this.totalServico = this.totalServico.add(servico.getValor());
        this.servicosRealizados.add(servico);
    }

    public String resumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("ID: ").append(this.id);
        resumo.append("\nNome: ").append(this.cliente.getNome());
        resumo.append("\nServiços: \n");
        for (Servico servico : this.servicosRealizados) {
            resumo.append(servico).append(" R$ ").append(servico.getValor()).append("\n");
        }
        resumo.append("Total do atendimento: ").append("R$ ").append(this.totalServico);
        return resumo.toString();
    }
    public ArrayList<Servico> getServicosRealizados() {
        return new ArrayList<Servico>(this.servicosRealizados);
    }

    public Pessoa getPessoa() {
        return this.cliente;
    }

    public int getId() {
        return this.id;
    }

    public BigDecimal getTotal() {
        return this.totalServico;
    }

    private static boolean containsServico(List<Servico> lista, Servico servico) {
        for (Servico service : lista) {
            if (servico == service) {
                return true;
            }
        }
        return false;
    }

}