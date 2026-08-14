package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.service.EstatisticasService;
import com.ysouz.gerenciadorbarbearia.util.Formatador;

public class EstatisticasController {
    private final EstatisticasService service;

    public EstatisticasController(EstatisticasService service) {
        if (service == null) throw new NullPointerException("O service de estatísticas não pode ser nulo.");
        this.service = service;
    }

    public void estatisticas() {
        Formatador.tituloDinamico("Estatísticas", 8);
        System.out.println(this.service.estatisticas());
    }
}
