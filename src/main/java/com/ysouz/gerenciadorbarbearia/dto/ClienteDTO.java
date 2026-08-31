package com.ysouz.gerenciadorbarbearia.dto;

public class ClienteDTO {
    private final String nome;
    private final int idade;
    private final String sexo;
    private final int totalAtendimentos;

    public ClienteDTO(String nome, int idade, String sexo, int totalAtendimentos) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.totalAtendimentos = totalAtendimentos;
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getSexo() {
        return this.sexo;
    }

    public int getTotalAtendimentos() {
        return this.totalAtendimentos;
    }
}
