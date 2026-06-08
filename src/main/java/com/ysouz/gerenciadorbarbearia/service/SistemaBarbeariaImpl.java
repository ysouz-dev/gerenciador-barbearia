package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.repository.*;

import java.util.List;

public final class SistemaBarbeariaImpl implements SistemaBarbearia {
    private ClienteDiarioRepository listaClientes;
    private AtendimentoRepository listaAtendimentos;
    private EstatisticasRepository estatisticas;

    public SistemaBarbeariaImpl() {
        this.listaClientes = new ClienteDiarioRepository();
        this.listaAtendimentos = new AtendimentoRepository();
        this.estatisticas = new EstatisticasRepository();
    }

    @Override
    public Pessoa buscaClientePorCpf(String cpf) {
        if (!this.listaClientes.containsCliente(cpf)) {
            throw new IllegalArgumentException("O sistema não possui um cliente com esse cpf");
        }
        return this.listaClientes.buscaPorCpf(cpf);
    }

    @Override
    public boolean containsAtendimento(Integer id) {
        return this.listaAtendimentos.containsAtendimento(id);
    }

    @Override
    public void cadastrarCliente(Pessoa pessoa) {
        if (this.listaClientes.containsCliente(pessoa.getCPF())) {
            throw new IllegalArgumentException("O sistema já possui um cliente cadastrado com esse CPF");
        }
        this.listaClientes.salvar(pessoa);
    }

    @Override
    public void cadastrarAtendimento(Atendimento atendimento) {
        if (atendimento.getServicosRealizados().isEmpty()) {
            throw new IllegalArgumentException("Não é possivel cadastrar um atendimento sem serviços realizados.");
        }
        this.listaAtendimentos.salvar(atendimento);
    }

    @Override
    public List<Pessoa> listarClientes() {
        if (this.listaClientes.listaDeClientes().isEmpty()) {
            throw new IllegalStateException("Nenhum cliente cadastrado no sistema.");
        }
        return this.listaClientes.listaDeClientes();
    }

    @Override
    public List<Atendimento> listarAtendimentos() {
        if (this.listaAtendimentos.listaDeAtendimento().isEmpty()) {
            throw new IllegalStateException("Nenhum atendimento cadastrado no sistema.");
        }
        return this.listaAtendimentos.listaDeAtendimento();
    }

    @Override
    public void removerCliente(String cpf) {
        if (!this.listaClientes.containsCliente(cpf)){
            throw new IllegalArgumentException("Cliente não está cadastrado no sistema.");
        }
        this.listaClientes.remover(cpf);
    }

    @Override
    public void removerAtendimento(Integer id) {
        if (!this.listaAtendimentos.containsAtendimento(id)) {
            throw new IllegalArgumentException("Atendimento não está cadastrado no sistema.");
        }
        this.listaAtendimentos.remover(id);
    }

    @Override
    public String estatisticas() {
        return "Total de clientes: " + this.estatisticas.totalClientes() +
                "\nTotal de Atendimentos: " + this.estatisticas.totalAtendimentos() +
                "\nTotal faturado: R$ " + this.estatisticas.totalFaturado();
    }
}
