package com.ysouz.gerenciadorbarbearia.util;

import com.ysouz.gerenciadorbarbearia.exception.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConexaoUtil {

    public static void rollback(Connection conexao, String mensagemErroRollback) {
        if (mensagemErroRollback == null) {
            throw new NullPointerException("A mensagem de erro ao realizar rollback não pode ser nula.");
        }

        if (conexao != null) {
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                throw new DatabaseException(mensagemErroRollback, ex);
            }
        }
    }

    public static void fechar(Connection conexao, String mensagemErroFecharConexao) {
        if (mensagemErroFecharConexao == null) {
            throw new NullPointerException("A mensagem de erro ao fechar conexão não pode ");
        }

        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.err.println(mensagemErroFecharConexao + e.getMessage());
            }
        }
    }
}
