package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando um cliente não é encontrado no sistema.
 * <p>
 * Diferente de {@link ClientesNaoEncontradosException}, essa exceção
 * se refere a apenas um cliente não encontrado.
 */
public class ClienteNaoEncontradoException extends RuntimeException{

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}
