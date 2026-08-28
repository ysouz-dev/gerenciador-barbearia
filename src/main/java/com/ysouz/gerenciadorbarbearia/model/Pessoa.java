package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.enums.Sexo;
import com.ysouz.gerenciadorbarbearia.validation.PessoaValidator;

/**
 * Representa uma pessoa no sistema.
 */
public abstract class Pessoa {
    private final String nome;
    private final int idade;
    private final String cpf;
    private final Sexo sexo;

    /**
     * Cria uma pessoa validando os dados informados.
     *
     * @param nome nome da pessoa
     * @param idade idade da pessoa
     * @param cpf cpf da pessoa
     * @param sexo sexo da pessoa
     * @throws IllegalArgumentException se algum dos dados informados forem inválidos ou nulos
     * (nome, idade, cpf, sexo)
     */
    public Pessoa(String nome, int idade, String cpf, Sexo sexo) {
        PessoaValidator.validaNome(nome);
        PessoaValidator.validaIdade(idade);
        PessoaValidator.validaCPF(cpf);
        if (sexo == null) throw new IllegalArgumentException("O sexo não pode ser nulo.");

        this.nome = nome.strip().toUpperCase();
        this.idade = idade;
        this.cpf = cpf.strip();
        this.sexo = sexo;
    }

    /**
     * Retorna uma ‘string’ com um resumo da pessoa.
     *
     * @return uma ‘string’ com os dados da pessoa
     */
    public String resumo() {
        return "Nome: " + this.nome +
                "\nIdade: " + this.idade +
                "\nCPF: " + this.cpf +
                "\nSexo: " + this.sexo.getNomeSexo();
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getCPF() {
        return this.cpf;
    }

    public Sexo getSexo() {
        return this.sexo;
    }
}
