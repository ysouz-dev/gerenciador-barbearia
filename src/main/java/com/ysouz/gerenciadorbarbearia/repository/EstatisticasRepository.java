package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.connection.Conexao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class EstatisticasRepository {

    public int totalClientes() {
        String query = "SELECT count(*) as total from clientes";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar total de clientes: " + e.getMessage());
        }
        return 0;
    }

    public int totalAtendimentos() {
        String query = "SELECT count(*) as total from atendimentos";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar total de atendimentos: " + e.getMessage());
        }
        return 0;
    }

    public BigDecimal totalFaturado() {
        String query = "SELECT coalesce(sum(valor), 0) as total from atendimentos";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar total faturado : " + e.getMessage());
        }
        return BigDecimal.ZERO;
    }
}
