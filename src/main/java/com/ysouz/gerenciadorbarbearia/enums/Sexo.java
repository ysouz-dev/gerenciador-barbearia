package com.ysouz.gerenciadorbarbearia.enums;

import java.util.Objects;

public enum Sexo {
    MASCULINO("MASCULINO", "M"),
    FEMININO("FEMININO", "F"),
    NAO_INFORMADO("NÃO INFORMADO", "N");

    private final String nome;
    private final String sigla;

    Sexo(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }


    public String getNomeSexo() {
        return this.nome;
    }

    public static Sexo toSexo(String sexo) {
        for (Sexo sex : Sexo.values()) {
            if (sexo.strip().equalsIgnoreCase(sex.nome) || sexo.strip().equalsIgnoreCase(sex.sigla)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Sexo inválido!");
    }
}
