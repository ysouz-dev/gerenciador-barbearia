package com.ysouz.gerenciadorbarbearia.model;

import com.ysouz.gerenciadorbarbearia.util.Validador;

public abstract class Pessoa {
    public enum Sexo {
        MASCULINO("MASCULINO", "M"),
        FEMININO("FEMININO", "F"),
        NAO_INFORMADO("NÃO INFORMADO", "N");

        private String nome;
        private String sigla;

        Sexo(String nome, String sigla) {
            this.nome = nome;
            this.sigla = sigla;
        }

        public Sexo toSexo(String sexo) {
            for (Sexo sex : Sexo.values()) {
                if (sexo.strip().equalsIgnoreCase(sex.nome) || sexo.strip().equalsIgnoreCase(sex.sigla)) {
                    return sex;
                }
            }
            throw new IllegalArgumentException("Sexo inválido!");
        }

        public static void isSexo(Sexo sexo) {
            if (sexo == null) {
                throw new IllegalArgumentException("Sexo inválido!");
            }
        }
    }

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

    public void resumo() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade);
        System.out.println("CPF: " + this.cpf);
        System.out.println("Sexo: " + this.sexo);
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
