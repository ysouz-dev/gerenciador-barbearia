package com.ysouz.gerenciadorbarbearia.connection;

import java.util.Properties;
import java.io.IOException;

public abstract class Conexao {
    private static final Properties prop = new Properties();

    static {
        try {
            prop.load(Conexao.class.getClassLoader().getResourceAsStream("dataabase.properties"));

        } catch (IOException e) {
            System.out.println("Erro ao carregar configurações do banco de dados: " + e.getMessage());
        }
    }

}
