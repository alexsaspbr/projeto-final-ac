package application;

import entities.Contato;
import entities.Telefone;

import java.util.Scanner;

public class Agenda {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showAgenda();
    }

    private static void showAgenda() {
        System.out.println("------------------");
        System.out.println("##################");
        System.out.println("######AGENDA######");
        System.out.println("##################\n\n");

        System.out.println(">>>> Contatos <<<<");
        System.out.println("ID | NOME");
        for (String contato : Contato.getContatos()) {
            showContato(contato);
        }
        System.out.println("------------------");
        System.out.println();

        showMenu();
    }

    public static void showContato(String contato) {
        String[] contatoString = contato.split("-");
        System.out.println(String.format("%s - %s %s", contatoString[0], contatoString[1], contatoString[2]));
    }

    private static void showMenu() {
        System.out.println(">>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Sair");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1 -> adicionarContato();
            case 2 -> removerContato();
            case 3 -> editarContato();
            case 4 -> scanner.close();
            default -> {
                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                showMenu();
            }
        }
    }

    private static void adicionarContato() {
        Contato contato = new Contato();
        Telefone telefone = setDados(contato);

        contato.setTelefone(telefone);
        contato.addContatoETelefone(telefone);
        continuarAdicionando(contato);
        showAgenda();
    }

    private static void continuarAdicionando(Contato contato) {
        boolean continuarAdd = true;
        while (continuarAdd) {
            System.out.println("Deseja adicionar mais um numero para esse contato? (1 - SIM | 2 - NÃO) ");
            int option = scanner.nextInt();

            switch (option) {
                case 1: {
                    scanner.nextLine();
                    System.out.println("\nDigite o ddd: ");
                    String dddNew = scanner.nextLine();

                    System.out.println("\nDigite o numero: ");
                    String numeroNew = scanner.nextLine();

                    Telefone novoTelefone = new Telefone(contato.getId(), dddNew, numeroNew);
                    contato.addTelefone(novoTelefone);
                    break;
                }
                case 2:
                    continuarAdd = false;
                    break;
                default:
                    System.out.println("Opção inválida, digite novamente.");
                    showMenu();
            }
        }
    }

    private static Telefone setDados(Contato contato) {
        System.out.println("\nDigite o primeiro nome do contato: ");
        contato.setNome(scanner.nextLine());
        System.out.println("\nDigite o sobrenome do contato: ");
        contato.setSobrenome(scanner.nextLine());
        System.out.println("\nDigite o ddd: ");
        String ddd = scanner.nextLine();
        System.out.println("\nDigite o numero: ");
        String numero = scanner.nextLine();

        Telefone telefone = new Telefone(contato.getId(), ddd, numero);
        return telefone;
    }

    private static void editarContato() {
        Contato contato = new Contato();
        Telefone telefone = setDados(contato);

        contato.setTelefone(telefone);
        Long id = getIdContato();
        contato.editarContato(id, telefone);

    }

    private static void removerContato() {
        Long id = getIdContato();
        Contato contato = new Contato();
        contato.removerContato(id);
        showAgenda();
    }

    private static Long getIdContato() {
        System.out.println("\nDigite o Id do contato: ");
        Long id = Long.parseLong(scanner.nextLine());
        return id;
    }
}
