package com.ysouz.gerenciadorbarbearia.util;

public abstract class Formatador {
    public static void titulo(String texto) {
        System.out.println("==       " + texto + "      ==");
    }

    public static void tituloDinamico(String texto, int tamanho) {
        linhaDinamica(tamanho);
        System.out.print(" ");
        System.out.print(texto);
        System.out.print(" ");
        linhaDinamica(tamanho, true);
    }

    public static void linha() {
        System.out.println("=============================");
    }

    public static void linhaDinamica(int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("=");
        }
    }

    public static void linhaDinamica(int tamanho, boolean pularLinha) {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("=");
            if (pularLinha) {
                if (i == tamanho - 1) {
                    System.out.println();
                }
            }
        }
    }
}
