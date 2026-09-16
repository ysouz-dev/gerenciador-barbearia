package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando um atendimento tenta ser cadastrado sem nenhum
 * serviço registrado.
 */
public class AtendimentoSemServicoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public AtendimentoSemServicoException(String message) {
        super(message);
    }
}
