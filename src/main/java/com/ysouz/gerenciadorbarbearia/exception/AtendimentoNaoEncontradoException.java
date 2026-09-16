package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando um atendimento não é encontrado no sistema.
 * <p>
 * Diferente de {@link AtendimentosNaoEncontradosException},
 * essa exceção se refere a apenas um atendimento.
 */
public class AtendimentoNaoEncontradoException extends RuntimeException {

    /**
     * Cria a exceção com a mensagem de erro.
     *
     * @param message descrição do erro
     */
    public AtendimentoNaoEncontradoException(String message) {
        super(message);
    }
}
