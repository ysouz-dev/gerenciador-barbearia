package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
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
        try {
            Connection conexao = Conexao.getConexao();
            String query = "SELECT * FROM clientes WHERE cpf = ?";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, cpf);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                String ClienteCPF = rs.getString("cpf");
                Sexo sexo = Sexo.NAO_INFORMADO;
                sexo = sexo.toSexo(rs.getString("sexo"));

                return new ClienteDiario(nome, idade, ClienteCPF, sexo);

            } else {
                throw new IllegalArgumentException("Nenhum cliente encontrado com esse cpf");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente no banco: " + e.getMessage());
        }
    }

    public boolean containsCliente(String cpf) {
        try {
            Connection conexao = Conexao.getConexao();
            String query = "SELECT nome FROM clientes WHERE cpf = ?";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setString(1, cpf);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se existe cliente no banco: " + e.getMessage());
        }
    }

    public ArrayList<Pessoa> listaDePessoas() {
        return new ArrayList<Pessoa>(this.listaPessoas.values());
    }

    public HashMap<String, Pessoa> getLista() {
        return new HashMap<String, Pessoa>(this.listaPessoas);
    }

}
