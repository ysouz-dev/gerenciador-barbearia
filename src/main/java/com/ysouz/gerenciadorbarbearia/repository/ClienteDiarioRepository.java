package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class ClienteDiarioRepository {
    private Map<String, Pessoa> listaPessoas;

    public ClienteDiarioRepository() {
        this.listaPessoas = new HashMap<>();
    }

    public void salvar(Pessoa pessoa) {
        try {
            Connection conexao = Conexao.getConexao();
            String query = "INSERT INTO clientes VALUES (?, ?, year(now()) - ?, ?)";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, pessoa.getCPF());
            statement.setString(2, pessoa.getNome());
            statement.setInt(3, pessoa.getIdade());
            statement.setString(4, pessoa.getSexo().getNomeSexo());
            statement.executeUpdate();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public void remover(String cpf) {
        try {
            Connection conexao = Conexao.getConexao();
            String query = "DELETE FROM clientes WHERE cpf = '?'";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, cpf);
            statement.executeUpdate();
            conexao.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover cliente: " + e.getMessage());
        }
    }

    public Pessoa buscaPorCpf(String cpf) {
        if (this.listaPessoas.containsKey(cpf)) {
            return this.listaPessoas.get(cpf);
        }
        throw new IllegalArgumentException("Nenhum cliente encontrado com esse cpf.");
    }

    public boolean containsPessoa(String cpf) {
        if (!this.listaPessoas.containsKey(cpf)) {
            return false;
        }
        return true;
    }

    public ArrayList<Pessoa> listaDePessoas() {
        return new ArrayList<Pessoa>(this.listaPessoas.values());
    }

    public HashMap<String, Pessoa> getLista() {
        return new HashMap<String, Pessoa>(this.listaPessoas);
    }

}
