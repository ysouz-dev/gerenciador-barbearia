package com.ysouz.gerenciadorbarbearia;

import com.ysouz.gerenciadorbarbearia.controller.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        int escolha = Integer.MIN_VALUE;
        while (escolha != 0) {
            escolha = menu.MenuPrincipal();

            switch (escolha) {
                case 1:
                    menu.cadastrarCliente();
                    break;

                case 2:
                    menu.cadastrarAtendimento();
                    break;

                case 3:
                    menu.listarClientes();
                    break;

                case 4:
                    menu.listarAtendimentos();
                    break;

                case 5:
                    menu.removerCliente();
                    break;

                case 6:
                    menu.removerAtendimento();
                    break;

                case 7:
                    menu.estatisticas();
                    break;

                case 0:
                    menu.encerrarSistema();
                    break;
            }
        }
    }
}
