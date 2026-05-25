package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;

import java.util.Map;
import java.util.ArrayList;

public class ClienteDiarioRepository {
    private Map<String, Pessoa> listaPessoas;

    public void salvar(ClienteDiario cliente) {
        this.listaPessoas.put(cliente.getCPF(), cliente);
    }

    public void remover(String cpf) {
        Pessoa c = buscaPorCpf(cpf);
        this.listaPessoas.remove(cpf, c);
    }

    public Pessoa buscaPorCpf(String cpf) {
        if (this.listaPessoas.containsKey(cpf)) {
            return this.listaPessoas.get(cpf);
        }
        throw new IllegalArgumentException("Nenhum cliente encontrado com esse cpf.");
    }

    public ArrayList<Pessoa> listarPessoas() {
        return new ArrayList<Pessoa>(this.listaPessoas.values());
    }


}
