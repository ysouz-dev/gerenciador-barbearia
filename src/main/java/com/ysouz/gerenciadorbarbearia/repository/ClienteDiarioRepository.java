package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;

import java.util.ArrayList;

public class ClienteDiarioRepository {
    private ArrayList<Pessoa> listaPessoas;

    public void salvar(ClienteDiario cliente) {
        this.listaPessoas.add(cliente);
    }

    public Pessoa buscaPorCpf(String cpf) {
        for (Pessoa pessoa : this.listaPessoas) {
            if (pessoa.getCPF().equals(cpf)) {
                return pessoa;
            }
        }
        throw new IllegalArgumentException("Nenhum cliente encontrado com esse cpf.");
    }


}
