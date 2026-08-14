package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.exception.ClienteJaCadastradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClienteNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClientesNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.repository.ClienteDiarioRepository;

import java.util.List;

public class ClienteDiarioService {
    private final ClienteDiarioRepository repository;

    public ClienteDiarioService(ClienteDiarioRepository repository) {
        this.repository = repository;
    }

    public Pessoa buscaClientePorCpf(String cpf) {
        if (!this.repository.containsCliente(cpf)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com esse cpf.");
        }
        return this.repository.buscaPorCpf(cpf);
    }

    public void cadastrarCliente(Pessoa pessoa) {
        if (this.repository.containsCliente(pessoa.getCPF())) {
            throw new ClienteJaCadastradoException("O sistema já possui um cliente cadastrado com esse cpf.");
        }
        this.repository.salvar(pessoa);
    }

    public List<Pessoa> listarClientes() {
        List<Pessoa> lista = this.repository.listaDeClientes();
        if (lista.isEmpty()) {
            throw new ClientesNaoEncontradosException("Nenhum cliente cadastrado no sistema.");
        }
        return lista;
    }

    public void removerCliente(String cpf) {
        if (!this.repository.containsCliente(cpf)){
            throw new ClienteNaoEncontradoException("Nenhum cliente com esse cpf está cadastrado no sistema.");
        }
        this.repository.remover(cpf);
    }
}
