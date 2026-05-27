package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;
import com.ysouz.gerenciadorbarbearia.repository.ClienteDiarioRepository;
import com.ysouz.gerenciadorbarbearia.repository.AtendimentoRepository;

import java.util.ArrayList;

public final class SistemaBarbeariaImpl implements SistemaBarbearia {
    private ClienteDiarioRepository listaClientes;
    private AtendimentoRepository listaAtendimentos;
    private Estatisticas estatisticas;

    public SistemaBarbeariaImpl() {
        this.listaClientes = new ClienteDiarioRepository();
        this.listaAtendimentos = new AtendimentoRepository();
        this.estatisticas = new Estatisticas();
    }

    public Pessoa buscaClientePorCpf(String cpf) {
        if (!this.listaClientes.getLista().containsKey(cpf)) {
            throw new IllegalArgumentException("O sistema não possui um cliente com esse cpf");
        }
        return this.listaClientes.buscaPorCpf(cpf);
    }

    public Atendimento buscaAtendimentoPorId(Integer id) {
        if (!this.listaAtendimentos.getLista().containsKey(id)) {
            throw new IllegalArgumentException("O sistema não possui um atendimento com esse ID");
        }
        return this.listaAtendimentos.buscaPorId(id);
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
        if (atendimento.getServicosRealizados().isEmpty()) {
            throw new IllegalArgumentException("Não é possivel cadastrar um atendimento sem serviços realizados.");
        }
        if (atendimento.getPessoa() instanceof ClienteDiario) {
            ((ClienteDiario) atendimento.getPessoa()).aumentarAtendimento();
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
    public String estatisticas() {
        return "Total de clientes: " + this.estatisticas.getTotalClientes() +
                "\nTotal de Atendimentos: " + this.estatisticas.getTotalAtendimentos() +
                "\nTotal faturado: R$ " + this.estatisticas.getTotalFaturado();
    }
}
