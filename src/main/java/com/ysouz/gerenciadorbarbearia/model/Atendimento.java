package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.enums.Servico;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa um atendimento no sistema.
 */
public class Atendimento {

    private Integer id;
    private List<Servico> servicosRealizados;
    private BigDecimal totalServico;
    private Pessoa cliente;

    /**
     * Cria um atendimento validando o cliente informado.
     *
     * @param cliente cliente a ser atendido
     * @throws IllegalArgumentException se o cliente informado for nulo
     */
    public Atendimento(Pessoa cliente) {
        if (Objects.isNull(cliente)){
            throw new IllegalArgumentException("O cliente não pode ser nulo.");
        }
        this.servicosRealizados = new ArrayList<>();
        this.cliente = cliente;
        this.id = null;
        this.totalServico = BigDecimal.ZERO;
    }

    /**
     * Cria um atendimento.
     *
     * @param id ID do atendimento
     * @param cliente cliente a ser atendido
     */
    public Atendimento(Integer id, Pessoa cliente) {
        this.servicosRealizados = new ArrayList<>();
        this.id = id;
        this.cliente = cliente;
        this.totalServico = BigDecimal.ZERO;
    }

    /**
     * Adiciona o serviço informado a lista de serviços do atendimento.
     *
     * @param servico serviço realizado
     * @throws IllegalArgumentException se o serviço informado já tiver sido realizado pelo cliente nesse atendimento,
     * ou o serviço informado for nulo
     */
    public void adicionarServico(Servico servico) {
        if (Objects.isNull(servico)) {
            throw new IllegalArgumentException("O serviço não pode ser nulo.");
        }
        if (containsServico(this.servicosRealizados, servico)) {
            throw new IllegalArgumentException("Esse serviço já foi registrado nesse atendimento.");
        }

        this.totalServico = this.totalServico.add(servico.getValor());
        this.servicosRealizados.add(servico);
    }

    /**
     * Retorna uma ‘string’ com os dados do atendimento.
     *
     * @return uma ‘string’ com os dados do atendimento
     */
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
        return new ArrayList<>(this.servicosRealizados);
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

    /**
     * Verifica se contém o serviço informado na lista informada.
     *
     * @param lista lista a ser verificada
     * @param servico serviço a ser buscado
     * @return true se o serviço estiver na lista, false caso o contrário
     */
    private static boolean containsServico(List<Servico> lista, Servico servico) {
        for (Servico service : lista) {
            if (servico == service) {
                return true;
            }
        }
        return false;
    }

}