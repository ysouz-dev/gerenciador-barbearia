package com.ysouz.gerenciadorbarbearia.exception;

public class AtendimentoSemServicoException extends RuntimeException {
    public AtendimentoSemServicoException(String message) {
        super(message);
    }

    public AtendimentoSemServicoException() {
        super("Atendimento sem serviços não podem ser cadastrados.");
    }
}
