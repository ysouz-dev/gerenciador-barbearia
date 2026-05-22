package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.util.Formatador;
import java.util.ArrayList;
import java.math.BigDecimal;

public final class SistemaBarbeariaImpl implements SistemaBarbearia {
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<Atendimento> listaAtendimentos;
    private BigDecimal totalFaturado;

    public SistemaBarbeariaImpl() {
        this.listaPessoas = new ArrayList<Pessoa>();
        this.listaAtendimentos = new ArrayList<Atendimento>();
        this.totalFaturado = BigDecimal.ZERO;
    }

    public Pessoa findPessoa(String cpf) {
        for (Pessoa people : this.listaPessoas) {
            if (people.getCPF().equals(cpf)) {
                return people;
            }
        }
        throw new IllegalArgumentException("Esse CPF não está cadastrado no sistema.");
    }

    public Atendimento findAtendimento(String id) {
        for (Atendimento atendimento : this.listaAtendimentos) {
            if (atendimento.getId().equals(id)) {
                return atendimento;
            }
        }
        throw new IllegalArgumentException("Id de atendimento inexistente ou incorreto.");
    }

    @Override
    public void cadastrarCliente(Pessoa pessoa) {
        if (containsPessoa(this.listaPessoas, pessoa)) {
            throw new IllegalArgumentException("O sistema já possui um cliente cadastrado com esse cpf.");
        }
        listaPessoas.add(pessoa);
    }

    @Override
    public void cadastrarAtendimento(Atendimento atendimento) {
        if (containsAtendimento(this.listaAtendimentos, atendimento)) {
            throw new IllegalArgumentException("O sistema já possui esse atendimento cadastrado.");
        }
        this.totalFaturado = this.totalFaturado.add(atendimento.getTotal());
        listaAtendimentos.add(atendimento);
    }

    @Override
    public void listarClientes() {
        if (this.listaPessoas.size() == 0) {
            throw new IllegalStateException("Nenhum cliente cadastrado no sistema.");
        }
        for (Pessoa pessoa : listaPessoas) {
            pessoa.resumo();
            Formatador.linha();
        }
    }

    @Override
    public void listarAtendimentos() {
        if (this.listaAtendimentos.size() == 0) {
            throw new IllegalStateException("Nenhum atendimento cadastrado no sistema.");
        }
        int contador = 1;
        for (Atendimento atendimento : this.listaAtendimentos) {
            System.out.println("N. " + contador);
            atendimento.resumo();
            contador++;
        }
    }

    @Override
    public void removerCliente(Pessoa pessoa) {
        if (!containsPessoa(this.listaPessoas, pessoa)) {
            throw new IllegalArgumentException("Cliente não está cadastrado no sistema.");
        }
        this.listaPessoas.remove(pessoa);
    }

    @Override
    public void removerAtendimento(Atendimento atendimento) {
        if (!containsAtendimento(this.listaAtendimentos, atendimento)) {
            throw new IllegalArgumentException("Atendimento não está cadastrado no sistema.");
        }
        this.totalFaturado = this.totalFaturado.subtract(atendimento.getTotal());
        this.listaAtendimentos.remove(atendimento);
    }

    @Override
    public void estatisticas() {
        System.out.println("Total de clientes: " + this.listaPessoas.size());
        System.out.println("Total de Atendimentos: " + this.listaAtendimentos.size());
        System.out.println("Total faturado: R$ %.2f".formatted(this.totalFaturado));
    }

    private static boolean containsPessoa(ArrayList<Pessoa> lista, Pessoa pessoa) {
        for (Pessoa people : lista) {
            if (pessoa.getCPF().equals(people.getCPF())) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsAtendimento(ArrayList<Atendimento> lista, Atendimento atendimento) {
        for (Atendimento atend : lista) {
            if (atendimento.getId().equals(atend.getId())) {
                return true;
            }
        }
        return false;
    }
}
