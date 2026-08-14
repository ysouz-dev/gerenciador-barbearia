package com.ysouz.gerenciadorbarbearia.validation;

public final class AtendimentoValidator {
    public static void validaId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo");
        }
        if (id < 1) {
            throw new IllegalArgumentException("O ID não pode ser menor ou igual a ZERO");
        }
    }
}
