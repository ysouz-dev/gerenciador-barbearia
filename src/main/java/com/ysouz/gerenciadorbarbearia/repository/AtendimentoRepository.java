package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Atendimento;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class AtendimentoRepository {
    private Map<Integer, Atendimento> listaAtendimento;

    public AtendimentoRepository() {
        this.listaAtendimento = new HashMap<>();
    }

    public void salvar(Atendimento atendimento) {
        this.listaAtendimento.put(atendimento.getId(), atendimento);
    }

    public void remover(Integer id) {
        Atendimento atend = buscaPorId(id);
        this.listaAtendimento.remove(id, atend);
    }

    public Atendimento buscaPorId(Integer id) {
        if (this.listaAtendimento.containsKey(id)) {
            return this.listaAtendimento.get(id);
        }
        throw new IllegalArgumentException("Nenhum atendimento encontrado com esse id");
    }

    public boolean containsAtendimento(Atendimento atendimento) {
        if (this.listaAtendimento.containsKey(atendimento.getId())) {
            return true;
        }
        return false;
    }

    public ArrayList<Atendimento> listaDeAtendimento() {
        return new ArrayList<Atendimento>(this.listaAtendimento.values());
    }

    public HashMap<Integer, Atendimento> getLista() {
        return new HashMap<Integer, Atendimento>(this.listaAtendimento);
    }

}
