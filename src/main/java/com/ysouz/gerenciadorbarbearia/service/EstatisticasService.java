package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.repository.EstatisticasRepository;

/**
 * Serviço responsável pelas estatísticas do sistema.
 */
public class EstatisticasService {
    private final EstatisticasRepository repository;

    public EstatisticasService(EstatisticasRepository repository) {
        if (repository == null) throw new NullPointerException("O repositório de estatística não pode ser nulo.");
        this.repository = repository;
    }

    /**
     * Retorna as principais estatísticas referentes ao estado atual do sistema.
     *
     * @return uma ‘string’ com as estatísticas do sistema
     */
    public String estatisticas() {
        return "Total de clientes: " + this.repository.totalClientes() +
                "\nTotal de Atendimentos: " + this.repository.totalAtendimentos() +
                "\nTotal faturado: R$ " + this.repository.totalFaturado();
    }
}
