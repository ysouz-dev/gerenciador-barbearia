package com.ysouz.gerenciadorbarbearia.controller;

import com.ysouz.gerenciadorbarbearia.enums.Sexo;
import com.ysouz.gerenciadorbarbearia.exception.ClienteJaCadastradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClienteNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClientesNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.service.ClienteDiarioService;
import com.ysouz.gerenciadorbarbearia.util.Formatador;
import com.ysouz.gerenciadorbarbearia.validation.PessoaValidator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ClienteDiarioController {
    private final ClienteDiarioService service;
    private final Scanner scanner;

    public ClienteDiarioController(ClienteDiarioService service, Scanner scanner) {
        if (service == null) throw new NullPointerException("O service de cliente não pode ser nulo.");
        if (scanner == null) throw new NullPointerException("O scanner de ClienteDiarioController não pode ser nulo.");

        this.service = service;
        this.scanner = scanner;
    }

    public void cadastrarCliente() {
        Formatador.tituloDinamico("Cadastro Cliente", 6);

        ClienteDiario cliente = null;
        String nome = "";
        int idade = Integer.MIN_VALUE;
        String cpf = "";
        Sexo sexo = null;

        int contador = 0;

        while (cliente == null) {
            try {
                if (contador == 0) {
                    System.out.print("Nome: ");
                    nome = this.scanner.nextLine();
                    PessoaValidator.validaNome(nome);
                    contador++;
                }

                if (contador == 1) {
                    System.out.print("Idade: ");
                    idade = this.scanner.nextInt();
                    PessoaValidator.validaIdade(idade);
                    this.scanner.nextLine();
                    contador++;
                }

                if (contador == 2) {
                    System.out.print("Cpf: ");
                    cpf = this.scanner.nextLine();
                    PessoaValidator.validaCPF(cpf);
                    contador++;
                }

                if (contador == 3) {
                    System.out.print("Sexo: ");
                    sexo = Sexo.toSexo(this.scanner.nextLine());
                    contador++;
                }

                cliente = new ClienteDiario(nome, idade, cpf, sexo);

                this.service.cadastrarCliente(cliente);
                System.out.println("Cliente cadastrado com sucesso!");

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número para a idade.");
                this.scanner.nextLine();

            } catch (ClienteJaCadastradoException e) {
                System.out.println("Erro: " + e.getMessage());
                return;
            }
        }
        Formatador.linha();
    }

    public void listarClientes() {
        try {
            for (Pessoa cliente : this.service.listarClientes()) {
                System.out.println(cliente.resumo());
                Formatador.linha();
            }
        } catch (ClientesNaoEncontradosException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerCliente() {
        Formatador.tituloDinamico("Remover Cliente", 6);

        while (true) {
            try {
                System.out.print("Digite o cpf do cliente: ");
                String cpf = this.scanner.nextLine();
                PessoaValidator.validaCPF(cpf);

                this.service.removerCliente(cpf);
                System.out.println("Cliente removido!");
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());

            } catch (ClienteNaoEncontradoException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
