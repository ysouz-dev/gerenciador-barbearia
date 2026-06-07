package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.model.Atendimento;
import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.enums.*;
import com.ysouz.gerenciadorbarbearia.model.ClienteDiario;

import java.math.BigDecimal;
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
        String query = "INSERT INTO atendimentos(cliente_cpf, valor) VALUES (?, ?) ";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, atendimento.getPessoa().getCPF());
            statement.setBigDecimal(2, atendimento.getTotal());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    String query2 = "INSERT INTO atendimentos_servicos(id_atendimento, id_servico) VALUES (?, ?)";
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
        String query = "DELETE FROM atendimentos WHERE id = ?";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query)) {
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
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String cpf = rs.getString("cpf");
                String nome = rs.getString("nome");
                int idade =  LocalDate.now().getYear() - rs.getInt("nascimento");
                Sexo sexo = Sexo.valueOf(rs.getString("sexo"));
                return new Atendimento(new ClienteDiario(nome, idade, cpf, sexo));

            } else {
                throw new IllegalArgumentException("Nenhum atendimento encontrado com esse ID");
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
            ResultSet rs = statement.executeQuery();
            return rs.next();

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
            PreparedStatement statement = conexao.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();

            Map<Integer, Atendimento> lista = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");

                if (!lista.containsKey(id)) {
                    String nome = rs.getString("nome");
                    String cpf = rs.getString("cpf");
                    int idade = LocalDate.now().getYear() - rs.getInt("nascimento");
                    Sexo sexo = Sexo.valueOf(rs.getString("sexo"));

                    Atendimento atendimento = new Atendimento(new ClienteDiario(nome, idade, cpf, sexo));
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
