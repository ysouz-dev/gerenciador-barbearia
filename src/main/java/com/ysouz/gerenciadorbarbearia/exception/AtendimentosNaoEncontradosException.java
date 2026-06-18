package com.ysouz.gerenciadorbarbearia.exception;

public class AtendimentosNaoEncontradosException extends RuntimeException {
    public AtendimentosNaoEncontradosException (String message) {
        super(message);
    }

    public AtendimentosNaoEncontradosException() {
        super("Nenhum atendimento foi encontrado.");
    }
}
