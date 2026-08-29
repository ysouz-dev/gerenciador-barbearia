package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.dto.ClienteDiarioDTO;
import com.ysouz.gerenciadorbarbearia.exception.ClienteJaCadastradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClienteNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.ClientesNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.repository.ClienteDiarioRepository;
import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;

import java.util.List;

/**
 * Serviço responsável pelas regras de negócios relacionadas aos clientes diários
 * incluindo cadastro, busca, listagem e remoção.
 */
public class ClienteDiarioService {
    private final ClienteDiarioRepository repository;

    public ClienteDiarioService(ClienteDiarioRepository repository) {
        if (repository == null) throw new NullPointerException("O repositório de cliente não pode ser nulo.");
        this.repository = repository;
    }

    /**
     * Busca o cliente no sistema conforme o cpf informado.
     *
     * @param cpf cpf do cliente
     * @return o cliente encontrado referente ao cpf informado
     * @throws ClienteNaoEncontradoException se nenhum cliente for encontrado com o cpf informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public Pessoa buscaClientePorCpf(String cpf) {
        return this.repository.buscaPorCpf(cpf);
    }

    /**
     * Cadastra o cliente informado no sistema.
     *
     * @param pessoa cliente a ser cadastrado
     * @throws ClienteJaCadastradoException se o sistema já possuir um cadastro com o mesmo cpf do cliente informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public void cadastrarCliente(Pessoa pessoa) {
        if (this.repository.containsCliente(pessoa.getCPF())) {
            throw new ClienteJaCadastradoException("O sistema já possui um cliente cadastrado com esse cpf.");
        }
        this.repository.salvar(pessoa);
    }

    /**
     * Lista todos os clientes cadastrados no sistema.
     *
     * @return uma lista com todos os clientes cadastrados no sistema
     * @throws ClientesNaoEncontradosException se nenhum cliente estiver cadastrado no sistema
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<ClienteDiarioDTO> listarClientes() {
        List<ClienteDiarioDTO> lista = this.repository.listaDeClientes();
        if (lista.isEmpty()) {
            throw new ClientesNaoEncontradosException("Nenhum cliente cadastrado no sistema.");
        }
        return lista;
    }

    /**
     * Remove do sistema o cliente referente ao cpf informado.
     *
     * @param cpf cpf do cliente a ser removido
     * @throws ClienteNaoEncontradoException se nenhum cliente for encontrado com o cpf informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void removerCliente(String cpf) {
        if (!this.repository.containsCliente(cpf)){
            throw new ClienteNaoEncontradoException("Nenhum cliente com esse cpf está cadastrado no sistema.");
        }
        this.repository.remover(cpf);
    }
}
