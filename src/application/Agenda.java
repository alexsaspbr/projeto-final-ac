package application;

import entities.Contato;
import entities.Telefone;

import java.util.Scanner;

public class Agenda {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarAgenda();
    }

    private static void mostrarAgenda() {
        System.out.println("##################");
        System.out.println("######AGENDA######");
        System.out.println("##################\n\n");

        System.out.println(">>>> Contatos <<<<");
        System.out.println("ID | NOME");
        for (String contato : Contato.getContatos()) {
            mostrarContato(contato);
        }
        System.out.println("------------------");
        System.out.println();

        mostrarMenu();
    }

    public static void mostrarContato(String contato) {
        String[] contatoString = contato.split("-");
        System.out.println(String.format("%s - %s %s", contatoString[0], contatoString[1], contatoString[2]));
    }

    private static void mostrarMenu() {
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
                mostrarMenu();
            }
        }
    }

    private static void adicionarContato() {
        Contato contato = new Contato();
        Telefone telefone = armazenarDados(contato);

        contato.setTelefone(telefone);
        contato.adicionarContatoETelefone(telefone);
        continuarAdicionando(contato);
        mostrarAgenda();
    }

    private static void continuarAdicionando(Contato contato) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Deseja adicionar mais um numero para esse contato? (1 - SIM | 2 - NÃO) ");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("\nDigite o ddd: ");
                    String dddNovo = scanner.nextLine();

                    System.out.println("\nDigite o numero: ");
                    String numeroNovo = scanner.nextLine();

                    Telefone novoTelefone = new Telefone(contato.getId(), dddNovo, numeroNovo);
                    contato.adicionarNovoTelefone(novoTelefone);
                    break;
                }
                case 2 -> continuar = false;
                default -> {
                    System.out.println("Opção inválida. Digite novamente.");
                    mostrarMenu();
                }
            }
        }
    }

    private static Telefone armazenarDados(Contato contato) {
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
        Telefone telefone = armazenarDados(contato);

        System.out.println("\nDigite o Id do contato que deseja editar: ");
        Long id = getIdContatoTelefone();

        contato.setTelefone(telefone);
        scanner.nextLine();

        boolean continuar = true;
        while (continuar) {
            System.out.println("Deseja editar algum telefone desse contato? (1 - SIM | 2 - NÃO)");
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> {
                    continuar = false;
                    System.out.println("\nDigite o Id do telefone que deseja editar: ");
                    Long idTelefone = getIdContatoTelefone();
                    contato.editarContato(id, telefone, idTelefone);
                    break;
                }
                case 2 -> {
                    continuar = false;
                    contato.editarContato(id, telefone);
                    break;
                }
                default -> System.out.println("Opção inválida. Digite novamente.");
            }
        }
    }

    private static void removerContato() {
        System.out.println("\nDigite o Id do contato que deseja remover: ");
        Long id = getIdContatoTelefone();
        Contato contato = new Contato();
        contato.removerContato(id);
        mostrarAgenda();
    }

    private static Long getIdContatoTelefone() {
        return Long.parseLong(scanner.nextLine());
    }

}
