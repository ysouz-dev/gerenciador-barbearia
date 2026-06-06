package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.enums.Servico;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Atendimento {
    private static int idGeral = 1;

    private int id;
    private ArrayList<Servico> servicosRealizados;
    private BigDecimal totalServico;
    private Pessoa cliente;

    public Atendimento(Pessoa cliente) {
        this.servicosRealizados = new ArrayList<Servico>();
        this.cliente = cliente;
        this.id = idGeral;
        this.totalServico = BigDecimal.ZERO;
        idGeral++;
    }

    public void adicionarServico(Servico servico) {
        Servico.isServico(servico);
        if (containsServico(servicosRealizados, servico)) {
            throw new IllegalArgumentException("Esse serviço já foi registrado nesse atendimento.");
        }

        this.totalServico = this.totalServico.add(servico.getValor());
        servicosRealizados.add(servico);
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

    public void setTotal(BigDecimal novoTotal) {
        if (novoTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O novo total do servico não pode ser menor ou igual a 0");
        }
        this.totalServico = novoTotal;
    }

    private static boolean containsServico(ArrayList<Servico> lista, Servico servico) {
        for (Servico service : lista) {
            if (servico == service) {
                return true;
            }
        }
        return false;
    }

}