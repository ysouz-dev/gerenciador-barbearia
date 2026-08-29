package com.ysouz.gerenciadorbarbearia.model;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

/**
 * Representa um cliente diário no sistema.
 */
public class Cliente extends Pessoa {
    private final int totalAtendimentos;

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
        this.totalAtendimentos = 0;
    }

    /**
     * Cria um cliente diário validando os dados informados.
     * <p>
     * Recebe o total de atendimentos como parâmetro no construtor.
     *
     * @param nome nome do cliente
     * @param idade idade do cliente
     * @param cpf cpf do cliente
     * @param sexo sexo do cliente
     * @param totalAtendimentos total de atendimentos do cliente
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos ou nulos
     * (nome, idade, cpf, sexo)
     */
    public Cliente(String nome, int idade, String cpf, Sexo sexo, int totalAtendimentos) {
        super(nome, idade, cpf, sexo);
        this.totalAtendimentos = totalAtendimentos;
    }

    /**
     * Retorna uma ‘string’ com um resumo do cliente.
     * <p>
     * Também retorna o total de atendimentos do cliente.
     *
     * @return uma ‘string’ com os dados do cliente
     */
    @Override
    public String resumo() {
        return super.resumo() + "\nTotal de atendimentos: " + this.totalAtendimentos;
    }
}
