package com.ysouz.gerenciadorbarbearia.exception;

/**
 * Lançada quando um cliente tenta se cadastrar no sistema
 * com um cpf já cadastrado anteriormente.
 */
public class ClienteJaCadastradoException extends RuntimeException {

    /**
     * Cria a exceção com uma mensagem de erro.
     *
     * @param message descrição do erro
     */
    public ClienteJaCadastradoException(String message) {
        super(message);
    }
}
