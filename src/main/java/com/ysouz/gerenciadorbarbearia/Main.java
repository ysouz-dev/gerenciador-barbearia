package com.ysouz.gerenciadorbarbearia;

import com.ysouz.gerenciadorbarbearia.controller.*;
import com.ysouz.gerenciadorbarbearia.repository.*;
import com.ysouz.gerenciadorbarbearia.service.*;

import java.util.Scanner;

public class Main {
    private static MenuController createMenu() {
        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepository();
        ClienteService clienteService = new ClienteService(clienteRepository);
        ClienteController clienteController = new ClienteController(clienteService, scanner);

        AtendimentoRepository atendimentoRepository = new AtendimentoRepository();
        AtendimentoService atendimentoService = new AtendimentoService(atendimentoRepository);
        AtendimentoController atendimentoController = new AtendimentoController(atendimentoService,
                clienteService, scanner);

        EstatisticasRepository estatisticasRepository = new EstatisticasRepository();
        EstatisticasService estatisticasService = new EstatisticasService(estatisticasRepository);
        EstatisticasController estatisticasController = new EstatisticasController(estatisticasService);

        return new MenuController(scanner, atendimentoController, clienteController, estatisticasController);
    }

    public static void main(String[] args) {
        MenuController menu = createMenu();
        menu.iniciar();
    }
}
