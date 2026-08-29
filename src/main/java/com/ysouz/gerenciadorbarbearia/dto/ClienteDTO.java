package com.ysouz.gerenciadorbarbearia.dto;

public class ClienteDTO {
    private final String nome;
    private final int idade;
    private final String sexo;

    public ClienteDTO(String nome, int idade, String sexo) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
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
}
