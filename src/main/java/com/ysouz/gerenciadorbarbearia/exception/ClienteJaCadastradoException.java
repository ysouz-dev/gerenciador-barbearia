package com.ysouz.gerenciadorbarbearia.exception;

public class ClienteJaCadastradoException extends RuntimeException {
    public ClienteJaCadastradoException(String message) {
        super(message);
    }

    public ClienteJaCadastradoException() {
        super("Cliente já cadastrado.");
    }
}
