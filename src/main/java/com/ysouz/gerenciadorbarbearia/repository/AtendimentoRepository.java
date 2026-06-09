package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.enums.*;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class AtendimentoRepository {

    public void salvar(Atendimento atendimento) {
        // query de inserção de atendimento
        String query = "INSERT INTO atendimentos(cliente_cpf, valor) VALUES (?, ?) ";

        // query que incrementa atendimento ao total de atendimentos do cliente
        String queryTotalAtendimento = "UPDATE clientes SET total_atendimentos = total_atendimentos + 1 WHERE cpf = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement statementTotalAtendimento = conexao.prepareStatement(queryTotalAtendimento)) {

            statement.setString(1, atendimento.getPessoa().getCPF());
            statement.setBigDecimal(2, atendimento.getTotal());
            statement.executeUpdate();

            statementTotalAtendimento.setString(1, atendimento.getPessoa().getCPF());
            statementTotalAtendimento.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {

                    // query que faz a associacao das tabelas atendimento e servicos
                    String query2 = "INSERT INTO atendimentos_servicos(id_atendimento, id_servico) VALUES (?, ?)";

                    // id do atendimento que esta sendo salvo
                    int idAtendimento = rs.getInt(1);

                    for (Servico servico : atendimento.getServicosRealizados()) {
                        try (PreparedStatement statement2 = conexao.prepareStatement(query2)){
                            statement2.setInt(1, idAtendimento);
                            statement2.setInt(2, List.of(Servico.values()).indexOf(servico) + 1);
                            statement2.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar atendimento: " + e.getMessage());
        }
    }

    public void remover(Integer id) {
        // query que deleta atendimentos
        String query = "DELETE FROM atendimentos WHERE id = ?";

        // query que deleta da tabela intermediária atendimentos_servicos
        String query2 = "DELETE FROM atendimentos_servicos WHERE id_atendimento = ?";

        // query que decrementa do total de atendimentos do cliente
        String query3 = "UPDATE clientes " +
                        "SET total_atendimentos = total_atendimentos - 1 " +
                        "WHERE cpf = (SELECT cliente_cpf from atendimentos WHERE id = ?)";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            PreparedStatement statement2 = conexao.prepareStatement(query2);
            PreparedStatement statement3 = conexao.prepareStatement(query3)) {

            statement3.setInt(1, id);
            statement3.executeUpdate();

            statement2.setInt(1, id);
            statement2.executeUpdate();

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover atendimento: " + e.getMessage());
        }
    }

    public Atendimento buscaPorId(Integer id) {
        String query = "SELECT cl.* FROM atendimentos as ate " +
                    "JOIN clientes as cl " +
                    "ON cl.cpf = ate.cliente_cpf " +
                    "WHERE ate.id = ?";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String cpf = rs.getString("cpf");
                    String nome = rs.getString("nome");
                    int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                    Sexo sexo = Sexo.valueOf(rs.getString("sexo"));
                    return new Atendimento(id, new ClienteDiario(nome, idade, cpf, sexo));

                } else {
                    throw new IllegalArgumentException("Nenhum atendimento encontrado com esse ID");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar atendimento: " + e.getMessage());
        }
    }

    public boolean containsAtendimento(Integer id) {
        String query = "SELECT id FROM atendimentos WHERE id = ?";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se atendimento está no banco: " + e.getMessage());
        }
    }

    public List<Atendimento> listaDeAtendimento() {
        String query = "SELECT ate.id, cl.*, sv.nome as servico FROM atendimentos_servicos as ates " +
                        "JOIN atendimentos as ate " +
                        "ON ate.id = ates.id_atendimento " +
                        "JOIN clientes as cl " +
                        "ON cl.cpf = ate.cliente_cpf " +
                        "JOIN servicos as sv " +
                        "ON sv.id = ates.id_servico";

        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            Map<Integer, Atendimento> lista = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");

                if (!lista.containsKey(id)) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                    Sexo sexo = Sexo.valueOf(rs.getString("sexo"));

                    Atendimento atendimento = new Atendimento(id, new ClienteDiario(nome, idade, cpf, sexo));
                    lista.put(id, atendimento);
                }
                lista.get(id).adicionarServico(Servico.valueOf(rs.getString("servico")));
            }
            return new ArrayList<>(lista.values());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar lista de atendimentos: " + e.getMessage());
        }
    }
}
