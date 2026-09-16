package com.ysouz.gerenciadorbarbearia.model;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

/**
 * Representa um cliente diário no sistema.
 */
public class Cliente extends Pessoa {

    /**
     * Cria um cliente diário validando os dados informados.
     *
     * @param nome nome do cliente
     * @param idade idade do cliente
     * @param cpf cpf do cliente
     * @param sexo sexo do cliente
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos ou nulos
     * (nome, idade, cpf, sexo)
     */
    public Cliente(String nome, int idade, String cpf, Sexo sexo) {
        super(nome, idade, cpf, sexo);
    }
}
