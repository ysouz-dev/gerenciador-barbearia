package com.ysouz.gerenciadorbarbearia.model;
import com.ysouz.gerenciadorbarbearia.enums.Sexo;

public class ClienteDiario extends Pessoa {
    private int totalAtendimentos;

    public ClienteDiario(String nome, int idade, String cpf, Sexo sexo) {
        super(nome, idade, cpf, sexo);
        this.totalAtendimentos = 0;
    }

    public void aumentarAtendimento() {
        this.totalAtendimentos++;
    }

    @Override
    public String resumo() {
        return super.resumo() + "\nTotal de atendimentos: " + this.totalAtendimentos;
    }
}
