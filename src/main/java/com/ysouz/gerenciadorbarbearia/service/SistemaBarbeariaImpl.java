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
    private BigDecimal totalFaturado;

    public SistemaBarbeariaImpl() {
        this.listaClientes = new ClienteDiarioRepository();
        this.listaAtendimentos = new AtendimentoRepository();
        this.totalFaturado = BigDecimal.ZERO;
    }

    @Override
    public void cadastrarCliente(Pessoa pessoa) {
        if (ClienteDiarioRepository.containsPessoa(pessoa, this.listaClientes.getLista())) {
            throw new IllegalArgumentException("O sistema já possui esse cliente cadastrado");
        }
        this.listaClientes.salvar(pessoa);
    }

    @Override
    public void cadastrarAtendimento(Atendimento atendimento) {
        if (AtendimentoRepository.containsAtendimento(atendimento, this.listaAtendimentos.getLista())) {
            throw new IllegalArgumentException("O sistema já possui esse atendimento cadastrado.");
        }
        this.totalFaturado = this.totalFaturado.add(atendimento.getTotal());
        this.listaAtendimentos.salvar(atendimento);
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
        if (!ClienteDiarioRepository.containsPessoa(pessoa, this.listaClientes.getLista())) {
            throw new IllegalArgumentException("Cliente não está cadastrado no sistema.");
        }
        this.listaClientes.remover(pessoa.getCPF());
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
}
