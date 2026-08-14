package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.repository.EstatisticasRepository;

public class EstatisticasService {
    private final EstatisticasRepository repository;

    public EstatisticasService(EstatisticasRepository repository) {
        if (repository == null) throw new NullPointerException("O repositório de estatística não pode ser nulo.");
        this.repository = repository;
    }

    public String estatisticas() {
        return "Total de clientes: " + this.repository.totalClientes() +
                "\nTotal de Atendimentos: " + this.repository.totalAtendimentos() +
                "\nTotal faturado: R$ " + this.repository.totalFaturado();
    }
}
