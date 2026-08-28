package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando mais de um atendimento não é encontrado no sistema.
 * <p>
 * Diferente de {@link AtendimentoNaoEncontradoException}, essa exceção
 * se refere a mais de um atendimento.
 */
public class AtendimentosNaoEncontradosException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public AtendimentosNaoEncontradosException (String message) {
        super(message);
    }

    public AtendimentosNaoEncontradosException(String message, Throwable cause) {
        super(message, cause);
    }
}
