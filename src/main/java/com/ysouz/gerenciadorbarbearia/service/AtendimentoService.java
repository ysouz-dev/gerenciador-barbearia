package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.exception.AtendimentoNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentoSemServicoException;
import com.ysouz.gerenciadorbarbearia.exception.AtendimentosNaoEncontradosException;
import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.repository.AtendimentoRepository;

import java.util.List;

public class AtendimentoService {
    private final AtendimentoRepository repository;

    public AtendimentoService(AtendimentoRepository repository) {
        this.repository = repository;
    }

    public boolean containsAtendimento(Integer id) {
        return this.repository.containsAtendimento(id);
    }

    public void cadastrarAtendimento(Atendimento atendimento) {
        if (atendimento.getServicosRealizados().isEmpty()) {
            throw new AtendimentoSemServicoException("Não é possível cadastrar um atendimento sem serviços realizados.");
        }
        this.repository.salvar(atendimento);
    }

    public List<Atendimento> listarAtendimentos() {
        List<Atendimento> lista = this.repository.listaDeAtendimento();
        if (lista.isEmpty()) {
            throw new AtendimentosNaoEncontradosException("Nenhum atendimento cadastrado no sistema.");
        }
        return lista;
    }

    public void removerAtendimento(Integer id) {
        if (!this.repository.containsAtendimento(id)) {
            throw new AtendimentoNaoEncontradoException("Atendimento não está cadastrado no sistema.");
        }
        this.repository.remover(id);
    }
}
