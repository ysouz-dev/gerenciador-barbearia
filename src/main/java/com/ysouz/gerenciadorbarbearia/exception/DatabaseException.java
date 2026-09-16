package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando ocorre alguma falha de comunicação com o banco de dados.
 */
public class DatabaseException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro e a causa original do erro.
     *
     * @param message descrição do erro
     * @param cause exceção original que motivou este erro
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
