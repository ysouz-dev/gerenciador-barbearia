package com.ysouz.gerenciadorbarbearia.model;

import java.math.BigDecimal;

public class Estatisticas {
    private int totalClientes;
    private int totalAtendimentos;
    private BigDecimal totalFaturado;

    public Estatisticas() {
        this.totalClientes = 0;
        this.totalAtendimentos = 0;
        this.totalFaturado = BigDecimal.ZERO;
    }

    public void incrementarCliente() {
        this.totalClientes++;
    }

    public void decrementarCliente() {
        this.totalClientes--;
    }

    public void incrementarAtendimento() {
        this.totalAtendimentos++;
    }

    public void decrementarAtendimento() {
        this.totalAtendimentos--;
    }

    public void adicionarValorFaturado(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor a ser adicionado no faturamento inválido.");
        }
        this.totalFaturado.add(valor);
    }

    public void removerValorFaturado(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor a ser removido do faturamento inválido");
        }
        this.totalFaturado.subtract(valor);
    }

    public int getTotalClientes() {
        return this.totalClientes;
    }

    public int getTotalAtendimentos() {
        return this.totalAtendimentos;
    }
    public BigDecimal getTotalFaturado() {
        return this.totalFaturado;
    }

}
