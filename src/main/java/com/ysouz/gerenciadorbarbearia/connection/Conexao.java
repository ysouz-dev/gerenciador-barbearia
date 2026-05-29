package com.ysouz.gerenciadorbarbearia.connection;

import java.util.Properties;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;


public abstract class Conexao {
    private static final Properties prop = new Properties();

    static {
        try {
            prop.load(Conexao.class.getClassLoader().getResourceAsStream("dataabase.properties"));

        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações do banco de dados: " + e.getMessage());
        }
    }

    public static Connection getConexao() {
        try {
            String url = prop.getProperty("database.url");
            String user = prop.getProperty("database.user");
            String pass = prop.getProperty("database.pass");

            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

}
