package com.ysouz.gerenciadorbarbearia.model;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Atendimento {
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

        public static void listarServicos() {
            Servico[] lista = Servico.values();
            for (int i = 0; i < lista.length; i++) {
                System.out.printf("[ %d ] - %s R$ %.2f%n", i + 1, lista[i], lista[i].getValor());
            }
        }

        public static void isServico(Servico servico) {
            if (servico == null) {
                throw new IllegalArgumentException("Serviço inválido!");
            }
        }
    }

    private static int idGeral = 1;

    private int id;
    private ArrayList<Servico> servicosRealizados;
    private BigDecimal totalServico;
    private Pessoa cliente;

    public Atendimento(Pessoa cliente) {
        this.servicosRealizados = new ArrayList<Servico>();
        this.cliente = cliente;
        this.id = idGeral;
        this.totalServico = BigDecimal.ZERO;
        idGeral++;
    }

    public void adicionarServico(Servico servico) {
        Servico.isServico(servico);
        if (containsServico(servicosRealizados, servico)) {
            throw new IllegalArgumentException("Esse serviço já foi registrado nesse atendimento.");
        }

        this.totalServico = this.totalServico.add(servico.getValor());
        servicosRealizados.add(servico);
    }

    public String resumo() {
        StringBuilder resumo = new StringBuilder();
        resumo.append("ID: ").append(this.id);
        resumo.append("\nNome: ").append(this.cliente.getNome());
        resumo.append("\nServiços: \n");
        for (Servico servico : this.servicosRealizados) {
            resumo.append(servico).append(" R$ ").append(servico.getValor()).append("\n");
        }
        resumo.append("Total do atendimento: ").append("R$ ").append(this.totalServico);
        return resumo.toString();
    }

    public Pessoa getPessoa() {
        return this.cliente;
    }

    public int getId() {
        return this.id;
    }

    public BigDecimal getTotal() {
        return this.totalServico;
    }

    private static boolean containsServico(ArrayList<Servico> lista, Servico servico) {
        for (Servico service : lista) {
            if (servico == service) {
                return true;
            }
        }
        return false;
    }

}