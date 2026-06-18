package com.ysouz.gerenciadorbarbearia.exception;

public class ClienteNaoEncontradoException extends RuntimeException{

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

    public ClienteNaoEncontradoException() {
        super("Cliente não encontrado.");
    }
}
