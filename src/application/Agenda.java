package application;

import entities.Contato;
import entities.Telefone;

import java.util.Scanner;

public class Agenda {

    // TODO [X] - ID DO Telefone sequencial e diferente do id do contato - OK
    // TODO [X] - Um contato pode ter mais de um telefone - OK
    // TODO [ ] - Implementar serialização nos ids

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
        System.out.println(String.format("%s - %s %s", contatoString[0].replace("C", ""), contatoString[1], contatoString[2]));
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
        System.out.println("\nDigite o primeiro nome do contato: ");
        contato.setNome(scanner.nextLine());
        System.out.println("\nDigite o sobrenome do contato: ");
        contato.setSobrenome(scanner.nextLine());
        System.out.println("\nDigite o ddd: ");
        String ddd = scanner.nextLine();
        System.out.println("\nDigite o numero: ");
        String numero = scanner.nextLine();

        Telefone telefone = new Telefone(contato.getId(), ddd, numero);

        contato.setTelefone(telefone);
        contato.addContatoETelefone(telefone);
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

                    Telefone telefoneNew = new Telefone(contato.getId(), dddNew, numeroNew);
                    contato.addTelefone(telefoneNew);
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
        showAgenda();
    }

    private static void editarContato() {
    }

    private static void removerContato() {
    }

    private static Long getIdContato() {
        System.out.println("\nDigite o Id do contato: ");
        return Long.parseLong(scanner.nextLine());
    }
}
