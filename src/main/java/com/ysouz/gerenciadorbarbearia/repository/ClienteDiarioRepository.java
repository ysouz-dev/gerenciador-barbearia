package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ClienteDiarioRepository {
    private Map<String, Pessoa> listaPessoas;

    public void salvar(Pessoa pessoa) {
        this.listaPessoas.put(pessoa.getCPF(), pessoa);
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

    public ArrayList<Pessoa> listaDePessoas() {
        return new ArrayList<Pessoa>(this.listaPessoas.values());
    }

    public HashMap<String, Pessoa> getLista() {
        return new HashMap<String, Pessoa>(this.listaPessoas);
    }

    public static boolean containsPessoa(Pessoa pessoa, Map<String, Pessoa> lista) {
        if (!lista.containsValue(pessoa)) {
            return false;
        }
        return true;
    }

}
