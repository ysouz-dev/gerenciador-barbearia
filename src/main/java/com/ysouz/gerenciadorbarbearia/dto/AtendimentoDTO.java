package com.ysouz.gerenciadorbarbearia.dto;

import com.ysouz.gerenciadorbarbearia.enums.Servico;

import java.math.BigDecimal;
import java.util.List;

public class AtendimentoDTO {
    private final Integer id;
    private final String nome;
    private final List<String> servicos;
    private final BigDecimal totalAtendimento;

    public AtendimentoDTO(Integer id, String nome, List<String> servicos, BigDecimal totalAtendimento) {
        this.id = id;
        this.nome = nome;
        this.servicos = servicos;
        this.totalAtendimento = totalAtendimento;
    }

    public String getNome() {
        return this.nome;
    }

    public Integer getId() {
        return this.id;
    }

    public BigDecimal getTotalAtendimento() {
        return this.totalAtendimento;
    }

    public List<String> getServicos() {
        return this.servicos;
    }
}
