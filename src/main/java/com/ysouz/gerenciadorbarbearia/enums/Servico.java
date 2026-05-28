package com.ysouz.gerenciadorbarbearia.enums;

import java.util.Objects;
import java.math.BigDecimal;

public enum Servico {
    CORTE(new BigDecimal("20")),
    PEZINHO(new BigDecimal("5")),
    SOBRANCELHA(new BigDecimal("5")),
    BARBA(new BigDecimal("10")),
    PIGMENTACAO(new BigDecimal("5")),
    LUZES(new BigDecimal("45")),
    PLATINADO(new BigDecimal("50"));

    private BigDecimal valor;

    Servico(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public static void listaDeServicos() {
        Servico[] lista = Servico.values();
        for (int i = 0; i < lista.length; i++) {
            System.out.printf("[ %d ] - %s R$ %.2f%n", i + 1, lista[i], lista[i].getValor());
        }
    }

    public static void isServico(Servico servico) {
        if (Objects.isNull(servico)) {
            throw new IllegalArgumentException("Serviço inválido!");
        }
    }
}
