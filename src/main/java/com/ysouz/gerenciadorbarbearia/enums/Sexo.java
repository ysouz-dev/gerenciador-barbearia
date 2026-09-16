package com.ysouz.gerenciadorbarbearia.enums;

/**
 * Representa o sexo do cliente no sistema.
 */
public enum Sexo {
    MASCULINO("MASCULINO", "M"),
    FEMININO("FEMININO", "F"),
    NAO_INFORMADO("NÃO INFORMADO", "N");

    private final String nome;
    private final String sigla;

    Sexo(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }


    public String getNomeSexo() {
        return this.nome;
    }

    /**
     * Converte a sigla (M/F/N) ou o nome (MASCULINO, FEMININO, NÃO INFORMADO) para o sexo correspondente.
     *
     * @param sexo sigla ou nome do sexo
     * @return o sexo correspondente a sigla ou nome informado
     * @throws IllegalArgumentException se a sigla ou o nome não corresponder a nenhum sexo válido
     */
    public static Sexo toSexo(String sexo) {
        for (Sexo sex : Sexo.values()) {
            if (sexo.strip().equalsIgnoreCase(sex.nome) || sexo.strip().equalsIgnoreCase(sex.sigla)) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Sexo inválido!");
    }
}
