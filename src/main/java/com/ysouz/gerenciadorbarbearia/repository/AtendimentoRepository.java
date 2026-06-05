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
    private Map<Integer, Atendimento> listaAtendimento;

    public AtendimentoRepository() {
        this.listaAtendimento = new HashMap<>();
    }

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
                Sexo sexo = Sexo.NAO_INFORMADO;
                sexo = sexo.toSexo(rs.getString("sexo"));
                return new Atendimento(new ClienteDiario(nome, idade, cpf, sexo));

            } else {
                throw new IllegalArgumentException("Nenhum atendimento encontrado com esse ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar atendimento: " + e.getMessage());
        }
    }

    public boolean containsAtendimento(Integer id) {
        if (this.listaAtendimento.containsKey(id)) {
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
