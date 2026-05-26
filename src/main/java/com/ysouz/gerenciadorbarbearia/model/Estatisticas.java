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

    public void incrementarAtendimento() {
        this.totalAtendimentos++;
    }
}
