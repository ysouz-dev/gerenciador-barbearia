package com.ysouz.gerenciadorbarbearia.dto;

public class ClienteDiarioDTO {
    private final String nome;
    private final int idade;
    private final String sexo;

    public ClienteDiarioDTO(String nome, int idade, String sexo) {
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getSexo() {
        return sexo;
    }
}
