package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.enums.Servico;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoSemServicoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentosNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.exception.ClienteNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.service.AtendimentoService;
import com.ysouz.gerenciadorbarbearia.service.ClienteDiarioService;
import com.ysouz.gerenciadorbarbearia.util.Formatador;
import com.ysouz.gerenciadorbarbearia.validation.AtendimentoValidator;
import com.ysouz.gerenciadorbarbearia.validation.PessoaValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AtendimentoController {
    private final Scanner scanner;
    private final AtendimentoService atendimentoservice;
    private final ClienteDiarioService clienteService;

    public AtendimentoController(AtendimentoService atendimentoService,
                                 ClienteDiarioService clienteService, Scanner scanner) {
        this.scanner = scanner;
        this.atendimentoservice = atendimentoService;
        this.clienteService = clienteService;
    }

    public void cadastrarAtendimento() {
        Formatador.tituloDinamico("Cadastro Atendimento", 4);

        while (true) {
            try {
                System.out.print("Cpf do cliente: ");
                String cpf = this.scanner.nextLine();
                PessoaValidator.validaCPF(cpf);

                Pessoa pessoa = this.clienteService.buscaClientePorCpf(cpf);
                Formatador.linha();

                Atendimento atendimento = new Atendimento(pessoa);

                adicionarServicos(atendimento);

                this.atendimentoservice.cadastrarAtendimento(atendimento);
                System.out.println("Atendimento cadastrado!");
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
                Formatador.linha();

            } catch (ClienteNaoEncontradoException | AtendimentoSemServicoException e) {
                System.out.println("Erro: " + e.getMessage());
                return;
            }
        }
    }

    private void adicionarServicos(Atendimento atendimento) {

        int opcao = Integer.MIN_VALUE;

        while (opcao != 0) {
            Servico[] lista = Servico.values();
            System.out.println(Servico.listaDeServicos());
            System.out.println("[ 0 ] - Finalizar");

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

            // adiciona o serviço ao atendimento
            if (opcao != 0) {
                try {
                    atendimento.adicionarServico(lista[opcao - 1]);

                } catch (IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }

    public void listarAtendimentos() {
        try {
            for (Atendimento atendimento : this.atendimentoservice.listarAtendimentos()) {
                System.out.println(atendimento.resumo());
                Formatador.linha();
            }
        } catch (AtendimentosNaoEncontradosException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerAtendimento() {
        Formatador.tituloDinamico("Remover Atendimento", 4);

        while (true) {
            try {
                System.out.print("Digite o id do atendimento: ");
                Integer id = this.scanner.nextInt();
                AtendimentoValidator.validaId(id);

                this.atendimentoservice.removerAtendimento(id);
                System.out.println("Atendimento removido!");
                break;

            } catch (InputMismatchException e) {
                System.out.println("Erro: digite apenas números.");

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (AtendimentoNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }


}
