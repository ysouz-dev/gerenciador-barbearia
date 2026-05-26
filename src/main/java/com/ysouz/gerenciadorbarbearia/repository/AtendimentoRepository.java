package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Atendimento;

import java.util.Map;

public class AtendimentoRepository {
    private Map<Integer, Atendimento> listaAtendimento;

    public void salvar(Atendimento atendimento) {
        this.listaAtendimento.put(atendimento.getId(), atendimento);
    }

    public Atendimento buscaPorId(Integer id) {
        if (this.listaAtendimento.containsKey(id)) {
            return this.listaAtendimento.get(id);
        }
        throw new IllegalArgumentException("Nenhum atendimento encontrado com esse id");
    }
}
