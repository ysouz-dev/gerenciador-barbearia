package com.ysouz.gerenciadorbarbearia.util;

public abstract class Validador {

    public static void validaCPF(String cpf) {
        if (cpf == null || cpf.isBlank() || cpf.strip().length() != 11) {
            throw new IllegalArgumentException("Cpf inválido!");
        }

        for (int i = 0; i < cpf.strip().length(); i++) {
            if (!Character.isDigit(cpf.strip().charAt(i))) {
                throw new IllegalArgumentException("Cpf inválido!");
            }
        }
    }

    public static void validaIdade(int idade) {
        if (idade < 1 || idade > 100) {
            throw new IllegalArgumentException("Idade inválida!");
        }
    }

    public static void validaNome(String nome) {
        if (nome == null || nome.isBlank() || nome.strip().length() < 3) {
            throw new IllegalArgumentException("Nome inválido!");
        }

        String nomeSemEspaco = nome.replace(" ", "");
        for (int i = 0; i < nomeSemEspaco.length(); i++) {
            if (!Character.isLetter(nomeSemEspaco.charAt(i))) {
                throw new IllegalArgumentException("Nome inválido!");
            }
        }
    }

    public static void validaId(String id) {
        if (id == null || id.isBlank() || !id.startsWith("#")) {
            throw new IllegalArgumentException("Id inválido! (Ex: #00)");
        }

        String idSemHash = id.replace("#", "");
        for (int i = 0; i < idSemHash.length(); i++) {
            if (!Character.isDigit(idSemHash.charAt(i))) {
                throw new IllegalArgumentException("Id inválido! (Ex: #00)");
            }
        }
    }
}
