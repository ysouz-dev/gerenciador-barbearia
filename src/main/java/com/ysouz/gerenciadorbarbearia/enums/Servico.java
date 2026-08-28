package com.ysouz.gerenciadorbarbearia.enums;

import java.math.BigDecimal;

/**
 * Representa os serviços disponíveis no sistema.
 */
public enum Servico {
    CORTE(new BigDecimal("20")),
    PEZINHO(new BigDecimal("5")),
    SOBRANCELHA(new BigDecimal("5")),
    BARBA(new BigDecimal("10")),
    PIGMENTACAO(new BigDecimal("5")),
    LUZES(new BigDecimal("45")),
    PLATINADO(new BigDecimal("50"));

    private final BigDecimal valor;

    Servico(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Retorna uma ‘string’ de uma lista ordenada dos serviços disponíveis.
     *
     * @return uma ‘string’ dos serviços disponíveis
     */
    public static String listaDeServicos() {
        Servico[] lista = Servico.values();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lista.length; i++) {
            sb.append("[ %d ] - %s R$ %.2f\n".formatted(i+1, lista[i], lista[i].getValor()));
        }
        return sb.toString();
    }
}
