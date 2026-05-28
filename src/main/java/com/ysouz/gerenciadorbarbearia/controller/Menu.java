package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.util.*;
import com.ysouz.gerenciadorbarbearia.service.SistemaBarbeariaImpl;
import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class Menu {
    private final Scanner scanner;
    private final SistemaBarbeariaImpl sistema;

    public Menu() {
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

    public void cadastrarCliente() {
        Formatador.tituloDinamico("Cadastro Cliente", 6);

        // leitura e validacao de nome
        String nome;
        while (true) {
            try {
                System.out.print("Nome: ");
                nome = this.scanner.nextLine();
                Validador.validaNome(nome);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // leitura e validacao de idade
        int idade;
        while (true) {
            try {
                System.out.print("Idade: ");
                idade = this.scanner.nextInt();
                Validador.validaIdade(idade);
                this.scanner.nextLine();
                break;

            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número para a idade.");
                this.scanner.nextLine();

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // leitura e validacao de cpf
        String cpf;
        while (true) {
            try {
                System.out.print("Cpf: ");
                cpf = this.scanner.nextLine();
                Validador.validaCPF(cpf);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // leitura e validacao de sexo
        Sexo sexo = Sexo.NAO_INFORMADO;
        while (true) {
            try {
                System.out.print("Sexo: ");
                sexo = sexo.toSexo(this.scanner.nextLine());
                Sexo.isSexo(sexo);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // tenta cadastrar o cliente no sistema
        try {
            this.sistema.cadastrarCliente(new ClienteDiario(nome, idade, cpf, sexo));
            System.out.println("Cliente cadastrado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        Formatador.linha();
    }

    public void cadastrarAtendimento() {
        Formatador.tituloDinamico("Cadastro Atendimento", 4);

        String cpf;
        Pessoa cliente;
        // leitura e validacao do cpf
        while (true) {
            try {
                System.out.print("Cpf do cliente: ");
                cpf = this.scanner.nextLine();
                Validador.validaCPF(cpf);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                Formatador.linha();
            }
        }

        // verifica se existe cliente com esse cpf no sistema
        try {
            cliente = this.sistema.buscaClientePorCpf(cpf);
            Formatador.linha();

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return;
        }

        Atendimento atendimento = new Atendimento(cliente);

        // looping de adicao de serviços ao atendimento
        int opcao = Integer.MIN_VALUE;
        while (opcao != 0) {
            Atendimento.Servico[] lista = Atendimento.Servico.values();
            Atendimento.Servico.listarServicos();
            System.out.println("[ 0 ] - Finalizar");

            // leitura e validacao da opcao desejada de servico
            do {
                Formatador.linha();
                System.out.print("Digite o número do serviço realizado: ");
                try {
                    opcao = this.scanner.nextInt();

                    if (opcao < 0 || opcao > lista.length) {
                        System.out.printf("Erro: %d não é uma opção válida.%n", opcao);
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Erro: Digite um número para a opção.");
                    this.scanner.nextLine();
                }
            } while (opcao < 0 || opcao > lista.length);

            // adiciona o servico ao atendimento
            if (opcao != 0) {
                try {
                    atendimento.adicionarServico(lista[opcao - 1]);

                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
        try {
            this.sistema.cadastrarAtendimento(atendimento);
            System.out.println("Atendimento cadastrado!");

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarClientes() {
        try {
            for (Pessoa cliente : this.sistema.listarClientes()) {
                System.out.println(cliente.resumo());
                Formatador.linha();
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarAtendimentos() {
        try {
            for (Atendimento atendimento : this.sistema.listarAtendimentos()) {
                System.out.println(atendimento.resumo());
                Formatador.linha();
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerCliente() {
        Formatador.tituloDinamico("Remover Cliente", 6);

        // leitura e validacao do cpf
        String cpf;
        while (true) {
            try {
                System.out.print("Digite o cpf do cliente: ");
                cpf = this.scanner.nextLine();
                Validador.validaCPF(cpf);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        // verifica se existe cliente com esse cpf no sistema
        Pessoa cliente;
        try {
            cliente = this.sistema.buscaClientePorCpf(cpf);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        // remove cliente do sistema
        try {
            this.sistema.removerCliente(cliente);
            System.out.println("Cliente removido!");
        } catch (IllegalArgumentException e) {
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

        // procura o atendimento de acordo com o id
        Atendimento atendimento;
        try {
            atendimento = this.sistema.buscaAtendimentoPorId(id);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            this.sistema.removerAtendimento(atendimento);
            System.out.println("Atendimento removido!");
        } catch (IllegalArgumentException e) {
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
