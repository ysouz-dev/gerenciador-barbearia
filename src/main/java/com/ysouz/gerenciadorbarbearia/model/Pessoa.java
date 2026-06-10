package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.util.Validador;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

public abstract class Pessoa {
    private String nome;
    private int idade;
    private final String cpf;
    private final Sexo sexo;

    public Pessoa(String nome, int idade, String cpf, Sexo sexo) {
        Validador.validaNome(nome);
        Validador.validaIdade(idade);
        Validador.validaCPF(cpf);
        Sexo.isSexo(sexo);

        this.nome = nome.strip().toUpperCase();
        this.idade = idade;
        this.cpf = cpf.strip();
        this.sexo = sexo;
    }

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
