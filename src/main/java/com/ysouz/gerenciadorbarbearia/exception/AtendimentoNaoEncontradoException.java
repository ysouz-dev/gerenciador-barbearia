package com.ysouz.gerenciadorbarbearia.exception;

public class AtendimentoNaoEncontradoException extends RuntimeException {
    public AtendimentoNaoEncontradoException(String message) {
        super(message);
    }

    public AtendimentoNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
