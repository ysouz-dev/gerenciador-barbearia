package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.repository.EstatisticasRepository;

public class EstatisticasService {
    private final EstatisticasRepository repository;

    public EstatisticasService(EstatisticasRepository repository) {
        this.repository = repository;
    }

    public String estatisticas() {
        return "Total de clientes: " + this.repository.totalClientes() +
                "\nTotal de Atendimentos: " + this.repository.totalAtendimentos() +
                "\nTotal faturado: R$ " + this.repository.totalFaturado();
    }
}
