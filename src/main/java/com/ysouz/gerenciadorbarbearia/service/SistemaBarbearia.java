package com.ysouz.gerenciadorbarbearia.service;

import com.ysouz.gerenciadorbarbearia.model.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Interface principal do sistema de gerenciamento da barbearia.
 * Define as operações disponíveis para clientes, atendimentos e estatísticas.
 */
public interface SistemaBarbearia {

    /**
     * Busca um cliente cadastrado pelo cpf.
     *
     * @param cpf CPF do cliente a ser buscado.
     * @return Pessoa encontrada com o CPF informado.
     * @throws IllegalArgumentException se nenhum cliente for encontrado com o CPF informado.
     */
    public Pessoa buscaClientePorCpf(String cpf);

    /**
     * Verifica se um atendimento está cadastrado no sistema.
     *
     * @param id ID do atendimento a ser verificado.
     * @return true se o atendimento existir, false caso contrário
     */
    public boolean containsAtendimento(Integer id);

    /**
     * Cadastra uma nova pessoa no sistema.
     *
     * @param pessoa Pessoa a ser cadastrada no sistema.
     * @throws IllegalArgumentException se já existir uma pessoa com o mesmo cpf no sistema.
     */
    public void cadastrarCliente(Pessoa pessoa);

    /**
     * Cadastra um novo atendimento no sistema.
     *
     * @param atendimento Atendimento a ser cadastrado no sistema.
     * @throws IllegalArgumentException se a lista de serviços realizado estiver vazia.
     */
    public void cadastrarAtendimento(Atendimento atendimento);

    /**
     * Retorna lista de todos os clientes cadastrados no sistema.
     *
     * @return Lista de clientes cadastrados.
     * @throws IllegalStateException se a lista de clientes estiver vazia.
     */
    public List<Pessoa> listarClientes();

    /**
     * Retorna lista de todos os atendimentos cadastrados no sistema.
     *
     * @return Lista de Atendimentos cadastrados.
     * @throws IllegalStateException se a lista de atendimentos estiver vazia.
     */
    public List<Atendimento> listarAtendimentos();

    /**
     * Remove um cliente do sistema pelo CPF.
     *
     * @param cpf CPF do cliente a ser removido do sistema.
     * @throws IllegalArgumentException se nenhum cliente for encontrado com esse CPF.
     */
    public void removerCliente(String cpf);

    /**
     * Remove um atendimento do sistema pelo ID.
     *
     * @param id ID do atendimetno a ser removido do sistema.
     * @throws IllegalArgumentException se nenhum atendimento for encontrado com esse ID.
     */
    public void removerAtendimento(Integer id);

    /**
     * Retorna um resumo geral das estatísticas do sistema.
     *
     * @return String formatada com as estatísticas do sistema
     */
    public String estatisticas();
}
