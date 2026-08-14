package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.util.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class MenuController {
    private final Scanner scanner;
    private final AtendimentoController atendimentoController;
    private final ClienteDiarioController clienteDiarioController;
    private final EstatisticasController estatisticasController;

    public MenuController(Scanner scanner, AtendimentoController atendimentoController,
                          ClienteDiarioController clienteDiarioController,
                          EstatisticasController estatisticasController) {

        this.scanner = scanner;
        this.atendimentoController = atendimentoController;
        this.clienteDiarioController = clienteDiarioController;
        this.estatisticasController = estatisticasController;
    }

    public int menuPrincipal() {
        Formatador.linha();
        Formatador.titulo("Barbearia YS");
        Formatador.linha();
        System.out.println("[ 1 ] Cadastrar Cliente");
        System.out.println("[ 2 ] Cadastrar Atendimento");
        System.out.println("[ 3 ] Listar Clientes");
        System.out.println("[ 4 ] Listar Atendimentos");
        System.out.println("[ 5 ] Remover Cliente");
        System.out.println("[ 6 ] Remover Atendimento");
        System.out.println("[ 7 ] Estatísticas");
        System.out.println("[ 0 ] Encerrar Sistema");

        Formatador.linha();

        int escolha = Integer.MIN_VALUE;
        do {
            try {
                System.out.print("Digite um número da opção: ");
                escolha = this.scanner.nextInt();
                this.scanner.nextLine();
                if (escolha < 0 || escolha > 7) {
                    System.out.printf("Erro: %d não é uma opção válida!%n", escolha);
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número das opções!");
                this.scanner.nextLine();
            }
            Formatador.linha();
        } while (escolha < 0 || escolha > 7);
        return escolha;
    }

    public void iniciar() {
        int escolha = Integer.MIN_VALUE;
        while (escolha != 0) {
            escolha = this.menuPrincipal();

            switch (escolha) {
                case 1:
                    this.clienteDiarioController.cadastrarCliente();
                    break;

                case 2:
                    this.atendimentoController.cadastrarAtendimento();
                    break;

                case 3:
                    this.clienteDiarioController.listarClientes();
                    break;

                case 4:
                    this.atendimentoController.listarAtendimentos();
                    break;

                case 5:
                    this.clienteDiarioController.removerCliente();
                    break;

                case 6:
                    this.atendimentoController.removerAtendimento();
                    break;

                case 7:
                    this.estatisticasController.estatisticas();
                    break;

                case 0:
                    this.scanner.close();
                    System.out.println("Sistema encerrado, volte sempre!");
                    break;
            }
        }
    }
}
