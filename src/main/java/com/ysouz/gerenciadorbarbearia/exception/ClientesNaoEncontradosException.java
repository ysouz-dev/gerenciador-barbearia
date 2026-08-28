package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando mais de um cliente não são encontrados no sistema.
 * <p>
 * Diferente de {@link ClienteNaoEncontradoException}, essa exceção
 * se refere a mais de um cliente não encontrado.
 */
public class ClientesNaoEncontradosException extends RuntimeException{
    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public ClientesNaoEncontradosException(String message) {
        super(message);
    }

    public ClientesNaoEncontradosException(String message, Throwable cause) {
        super(message, cause);
    }
}
