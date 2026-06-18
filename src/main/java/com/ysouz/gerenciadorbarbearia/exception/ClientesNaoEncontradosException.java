package com.ysouz.gerenciadorbarbearia.exception;

public class ClientesNaoEncontradosException extends RuntimeException{
    public ClientesNaoEncontradosException(String message) {
        super(message);
    }

    public ClientesNaoEncontradosException(String message, Throwable cause) {
        super(message, cause);
    }
}
