package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;
import com.ysouz.gerenciadorbarbearia.model.Pessoa;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;
import com.ysouz.gerenciadorbarbearia.exception.ClienteNaoEncontradoException;
import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;

import java.util.Objects;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ClienteDiarioRepository {

    public void salvar(Pessoa pessoa) {
        String query = "INSERT INTO clientes VALUES (?, ?, year(now()) - ?, ?, default)";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setString(1, pessoa.getCPF());
            statement.setString(2, pessoa.getNome());
            statement.setInt(3, pessoa.getIdade());
            statement.setString(4, pessoa.getSexo().getNomeSexo());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        }
    }

    public void remover(String cpf) {

        // query que remove o cliente
        String query = "DELETE FROM clientes WHERE cpf = ?";

        //query que remove o atendimento referente ao cliente
        String queryDeleteAtendimentos = "DELETE FROM atendimentos WHERE cliente_cpf = ?";

        // query que remove o dado referente ao atendimento/servico da tabela intermediária
        String queryDeleteAtendimentosServicos = "DELETE FROM atendimentos_servicos " +
                                                 "WHERE id_atendimento IN (" +
                                                 "SELECT id FROM atendimentos WHERE cliente_cpf = ?)";

        Connection conexao = null;
        try {
            conexao = Conexao.getConexao();
            conexao.setAutoCommit(false);

            try (PreparedStatement statement = conexao.prepareStatement(query);
                 PreparedStatement statementDeleteAtendimentos = conexao.prepareStatement(queryDeleteAtendimentos);
                 PreparedStatement statementDeleteAtendimentosServicos = conexao.prepareStatement(queryDeleteAtendimentosServicos)) {

                statementDeleteAtendimentosServicos.setString(1, cpf);
                statementDeleteAtendimentosServicos.executeUpdate();

                statementDeleteAtendimentos.setString(1, cpf);
                statementDeleteAtendimentos.executeUpdate();

                statement.setString(1, cpf);
                statement.executeUpdate();

                conexao.commit();
            }

        } catch (Exception e) {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.rollback();
                } catch (SQLException ex) {
                    throw new DatabaseException("Erro ao realizar rollback", ex);
                }
            }
            throw new DatabaseException("Erro ao remover cliente", e);
        } finally {
            if (!Objects.isNull(conexao)) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexao" + e.getMessage());
                }
            }
        }
    }

    public Pessoa buscaPorCpf(String cpf) {
        String query = "SELECT * FROM clientes WHERE cpf = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)){

            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    String nome = rs.getString("nome");
                    int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                    String ClienteCPF = rs.getString("cpf");
                    Sexo sexo = Sexo.valueOf(rs.getString("sexo"));
                    return new ClienteDiario(nome, idade, ClienteCPF, sexo);

                } else {
                    throw new ClienteNaoEncontradoException("Nenhum cliente encontrado com esse cpf");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente no banco: " + e.getMessage());
        }
    }

    public boolean containsCliente(String cpf) {
        String query = "SELECT nome FROM clientes WHERE cpf = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setString(1, cpf);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se existe cliente no banco: " + e.getMessage());
        }
    }

    public List<Pessoa> listaDeClientes() {
        String query = "SELECT * FROM clientes";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()){

            List<Pessoa> lista = new ArrayList<>();
            while (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                Sexo sexo = Sexo.toSexo(rs.getString("sexo"));
                int totalAtendimentos = rs.getInt("total_atendimentos");

                ClienteDiario cliente = new ClienteDiario(nome, idade, cpf, sexo, totalAtendimentos);

                lista.add(cliente);
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar lista de clientes no banco: " + e.getMessage());
        }
    }
}
