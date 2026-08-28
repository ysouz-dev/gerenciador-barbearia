package com.ysouz.gerenciadorbarbearia.repository;

import com.ysouz.gerenciadorbarbearia.connection.Conexao;
import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Repositório responsável por buscar as estatísticas do sistema.
 */
public class EstatisticasRepository {

    /**
     * Retorna o total de clientes registrados no sistema.
     *
     * @return número total de clientes registrados
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public int totalClientes() {
        String query = "SELECT count(*) as total from clientes";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retornar total de clientes.", e);
        }
        return 0;
    }

    /**
     * Retorna o total de atendimentos registrados no sistema.
     *
     * @return número total de atendimentos registrados
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public int totalAtendimentos() {
        String query = "SELECT count(*) as total from atendimentos";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retornar total de atendimentos", e);
        }
        return 0;
    }

    /**
     * Retorna o total faturado referente a todos os atendimentos do sistema.
     *
     * @return valor total faturado
     * @throws DatabaseException se ocorrer um erro ao acessar o banco de dados
     */
    public BigDecimal totalFaturado() {
        String query = "SELECT coalesce(sum(valor), 0) as total from atendimentos";
        try (Connection conexao = Conexao.getConexao();
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retornar total faturado.", e);
        }
        return BigDecimal.ZERO;
    }
}
