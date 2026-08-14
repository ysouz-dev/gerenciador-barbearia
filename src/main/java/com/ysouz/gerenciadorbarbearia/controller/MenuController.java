package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.util.*;
import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.exception.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class MenuController {
    private final Scanner scanner;
    private final SistemaBarbearia sistema;

    public MenuController() {
        this.scanner = new Scanner(System.in);
        this.sistema = new SistemaBarbeariaImpl();
    }

    public int MenuPrincipal() {
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


    public void listarAtendimentos() {
        try {
            for (Atendimento atendimento : this.sistema.listarAtendimentos()) {
                System.out.println(atendimento.resumo());
                Formatador.linha();
            }
        } catch (AtendimentosNaoEncontradosException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerAtendimento() {
        Formatador.tituloDinamico("Remover Atendimento", 4);

        // leitura e validacao do id
        Integer id;
        while (true) {
            try {
                System.out.print("Digite o id do atendimento: ");
                id = this.scanner.nextInt();
                Validador.validaId(id);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("Erro: digite apenas números.");
                this.scanner.nextLine();
            }
        }

        // verifica se existe atendimento com o ID antes de remover
        if (!this.sistema.containsAtendimento(id)) {
            System.out.println("Erro: Atendimento não encontrado no sistema");
            return;
        }

        // remove atendimento
        try {
            this.sistema.removerAtendimento(id);
            System.out.println("Atendimento removido!");
        } catch (AtendimentoNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void estatisticas() {
        Formatador.tituloDinamico("Estatísticas", 8);
        System.out.println(this.sistema.estatisticas());
    }

    public void encerrarSistema() {
        System.out.println("Sistema encerrado. Volte sempre!");
        this.scanner.close();
    }
}
