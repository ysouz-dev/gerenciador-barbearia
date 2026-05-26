package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.util.Formatador;
import com.ysouz.gerenciadorbarbearia.repository.ClienteDiarioRepository;
import com.ysouz.gerenciadorbarbearia.repository.AtendimentoRepository;

import java.util.ArrayList;
import java.math.BigDecimal;

public final class SistemaBarbeariaImpl implements SistemaBarbearia {
    private ClienteDiarioRepository listaClientes;
    private AtendimentoRepository listaAtendimentos;
    private Estatisticas estatisticas;

    public SistemaBarbeariaImpl() {
        this.listaClientes = new ClienteDiarioRepository();
        this.listaAtendimentos = new AtendimentoRepository();
        this.estatisticas = new Estatisticas();
    }

    @Override
    public void cadastrarCliente(Pessoa pessoa) {
        if (this.listaClientes.containsPessoa(pessoa)) {
            throw new IllegalArgumentException("O sistema já possui esse cliente cadastrado");
        }
        this.listaClientes.salvar(pessoa);
        this.estatisticas.incrementarCliente();
    }

    @Override
    public void cadastrarAtendimento(Atendimento atendimento) {
        if (this.listaAtendimentos.containsAtendimento(atendimento)) {
            throw new IllegalArgumentException("O sistema já possui esse atendimento cadastrado.");
        }
        this.listaAtendimentos.salvar(atendimento);
        this.estatisticas.incrementarAtendimento();
        this.estatisticas.adicionarValorFaturado(atendimento.getTotal());
    }

    @Override
    public ArrayList<Pessoa> listarClientes() {
        if (this.listaClientes.getLista().isEmpty()) {
            throw new IllegalStateException("Nenhum cliente cadastrado no sistema.");
        }
        return this.listaClientes.listaDePessoas();
    }

    @Override
    public ArrayList<Atendimento> listarAtendimentos() {
        if (this.listaAtendimentos.getLista().isEmpty()) {
            throw new IllegalStateException("Nenhum atendimento cadastrado no sistema.");
        }
        return this.listaAtendimentos.listaDeAtendimento();
    }

    @Override
    public void removerCliente(Pessoa pessoa) {
        if (!this.listaClientes.containsPessoa(pessoa)) {
            throw new IllegalArgumentException("Cliente não está cadastrado no sistema.");
        }
        this.listaClientes.remover(pessoa.getCPF());
        this.estatisticas.decrementarCliente();
    }

    @Override
    public void removerAtendimento(Atendimento atendimento) {
        if (!this.listaAtendimentos.containsAtendimento(atendimento)) {
            throw new IllegalArgumentException("Atendimento não está cadastrado no sistema.");
        }
        this.listaAtendimentos.remover(atendimento.getId());
        this.estatisticas.removerValorFaturado(atendimento.getTotal());
        this.estatisticas.decrementarAtendimento();
    }

    @Override
    public void estatisticas() {
        System.out.println("Total de clientes: " + this.listaPessoas.size());
        System.out.println("Total de Atendimentos: " + this.listaAtendimentos.size());
        System.out.println("Total faturado: R$ %.2f".formatted(this.totalFaturado));
    }
}
