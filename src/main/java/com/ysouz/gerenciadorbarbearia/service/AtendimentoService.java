package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.dto.AtendimentoDTO;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoSemServicoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentosNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.repository.AtendimentoRepository;
import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;

import java.util.List;

/**
 * Serviço responsável pelas regras de negócios relacionadas aos atendimentos
 * incluindo cadastro, listagem e remoção.
 */
public class AtendimentoService {
    private final AtendimentoRepository repository;

    public AtendimentoService(AtendimentoRepository repository) {
        if (repository == null) throw new NullPointerException("O repositório de atendimento não pode ser nulo.");
        this.repository = repository;
    }

    /**
     * Cadastra o atendimento informado no sistema.
     *
     * @param atendimento atendimento a ser cadastrado
     * @throws AtendimentoSemServicoException se o atendimento informado não tiver nenhum serviço registrado
     * @throws DatabaseException se ocorrer um erro ao acessar banco de dados ou ao realizar rollback de transação
     */
    public void cadastrarAtendimento(Atendimento atendimento) {
        if (atendimento.getServicosRealizados().isEmpty()) {
            throw new AtendimentoSemServicoException("Não é possível cadastrar um atendimento sem serviços realizados.");
        }
        this.repository.salvar(atendimento);
    }

    /**
     * Lista todos os atendimentos cadastrados no sistema.
     *
     * @return uma lista de todos os atendimentos cadastrados
     * @throws AtendimentosNaoEncontradosException se o sistema não possuir nenhum atendimento cadastrado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public List<AtendimentoDTO> listarAtendimentos() {
        List<AtendimentoDTO> lista = this.repository.listaDeAtendimento();
        if (lista.isEmpty()) {
            throw new AtendimentosNaoEncontradosException("Nenhum atendimento cadastrado no sistema.");
        }
        return lista;
    }

    /**
     * Remove do sistema o atendimento referente ao ID informado.
     *
     * @param id ID do atendimento
     * @throws AtendimentoNaoEncontradoException se nenhum atendimento for encontrado com o ID informado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados ou ao realizar rollback de transação
     */
    public void removerAtendimento(Integer id) {
        if (!this.repository.containsAtendimento(id)) {
            throw new AtendimentoNaoEncontradoException("Atendimento não está cadastrado no sistema.");
        }
        this.repository.remover(id);
    }
}
