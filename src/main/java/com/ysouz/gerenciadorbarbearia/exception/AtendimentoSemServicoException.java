package com.ysouz.gerenciadorbarbearia.exception;

public class AtendimentoSemServicoException extends RuntimeException {
    public AtendimentoSemServicoException(String message) {
        super(message);
    }

    public AtendimentoSemServicoException(String message, Throwable cause) {
        super(message, cause);
    }
}
