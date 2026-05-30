package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;

import java.util.ArrayList;

public interface SistemaBarbearia {
    public void cadastrarCliente(Pessoa pessoa);

    public void cadastrarAtendimento(Atendimento atendimento);

    public ArrayList<Pessoa> listarClientes();

    public ArrayList<Atendimento> listarAtendimentos();

    public void removerCliente(String cpf);

    public void removerAtendimento(Atendimento atendimento);

    public String estatisticas();
}
