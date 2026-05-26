package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Atendimento;

import java.util.Map;

public class AtendimentoRepository {
    private Map<Integer, Atendimento> listaAtendimento;

    public void salvar(Atendimento atendimento) {
        this.listaAtendimento.put(atendimento.getId(), atendimento);
    }
}
