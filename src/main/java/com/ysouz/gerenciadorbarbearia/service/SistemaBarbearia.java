package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;

import java.util.ArrayList;
import java.util.List;

public interface SistemaBarbearia {
    public Pessoa buscaClientePorCpf(String cpf);

    public boolean containsAtendimento(Integer id);

    public void cadastrarCliente(Pessoa pessoa);

    public void cadastrarAtendimento(Atendimento atendimento);

    public List<Pessoa> listarClientes();

    public List<Atendimento> listarAtendimentos();

    public void removerCliente(String cpf);

    public void removerAtendimento(Integer id);

    public String estatisticas();
}
