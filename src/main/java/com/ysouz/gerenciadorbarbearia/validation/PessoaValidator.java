package com.ysouz.gerenciadorbarbearia.validation;

public final class PessoaValidator {

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
}
