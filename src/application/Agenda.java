package application;

import entities.Contato;
import entities.Telefone;

import java.util.List;
import java.util.Scanner;

// PROJETO: Lógica de Programação I - Agenda de Contatos
// Rafael Santos Isidro
public class Agenda {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        mostrarAgenda();
    }

    public static void mostrarAgenda() {
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

    public static void mostrarMenu() {
        System.out.println(">>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Sair");

        int option = Integer.parseInt(scanner.nextLine());

        switch (option) {
            case 1 -> adicionarContato();
            case 2 -> removerContato();
            case 3 -> editarContato();
            case 4 -> {
                return;
            }
            default -> {
                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                mostrarMenu();
            }
        }
    }

    private static void adicionarContato() {
        Contato contato = new Contato();
        Telefone telefone = armazenarDados(contato, contato.getId());

        contato.setTelefone(telefone);
        contato.adicionarContatoETelefone(telefone);

        continuarAdicionando(contato.getId());
        mostrarAgenda();
    }

    private static void continuarAdicionando(Long id) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Deseja adicionar mais um numero para esse contato? (1 - SIM | 2 - NÃO) ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.println("\nDigite o ddd: ");
                    String dddNovo = scanner.nextLine();

                    System.out.println("\nDigite o numero: ");
                    String numeroNovo = scanner.nextLine();

                    List<String> telefones = Contato.getTelefones();

                    boolean dddExistente = telefones.stream()
                            .anyMatch(line -> line.split("-")[2].equals(dddNovo));
                    boolean numeroExistente = telefones.stream()
                            .anyMatch(line -> line.split("-")[3].equals(numeroNovo));

                    if (dddExistente && numeroExistente) {
                        System.out.println("Esse número já está cadastrado, tente novamente.");
                        mostrarMenu();
                    }

                    Telefone novoTelefone = new Telefone(id, dddNovo, numeroNovo);
                    Contato.adicionarNovoTelefone(novoTelefone, id);
                }
                case 2 -> {
                    continuar = false;
                }
                default -> {
                    System.out.println("Opção inválida. Digite novamente.");
                    mostrarMenu();
                }
            }
        }
    }

    private static void editarContato() {
        Contato contato = new Contato();

        System.out.println("\nDigite o Id do contato que deseja editar: ");
        Long id = utilities.Contato.getIdInserido();
        utilities.Contato.checarContatoExistente(contato, id);

        Telefone telefone = armazenarDados(contato, id);

        contato.setTelefone(telefone);
        contato.editarContato(id, telefone);

        continuarAdicionando(id);

        System.out.println("\n-----Contato editado com sucesso!-----\n");

        mostrarAgenda();
    }

    private static void removerContato() {
        System.out.println("\nDigite o Id do contato que deseja remover: ");
        Long idContato = utilities.Contato.getIdInserido();

        Contato contato = new Contato();

        utilities.Contato.checarContatoExistente(contato, idContato);

        contato.removerContato(idContato);

        System.out.println("\n-----Contato removido com sucesso!-----\n");
        mostrarAgenda();
    }

    private static Telefone armazenarDados(Contato contato, Long idInserido) {
        System.out.println("\nDigite o primeiro nome do contato: ");
        contato.setNome(scanner.nextLine());
        System.out.println("\nDigite o sobrenome do contato: ");
        contato.setSobrenome(scanner.nextLine());
        System.out.println("\nDigite o ddd: ");
        String ddd = scanner.nextLine();
        System.out.println("\nDigite o numero: ");
        String numero = scanner.nextLine();

        List<String> telefones = Contato.getTelefones();
        boolean telefoneExistente = false;

        if (telefones.stream().anyMatch(
                line -> !String.valueOf(idInserido).equals(line.split("-")[0].replace("C", ""))
                        && ddd.equals(line.split("-")[2])
                        && numero.equals(line.split("-")[3]))) {
            telefoneExistente = true;
        }

        if (telefoneExistente) {
            System.out.println("Esse número já está cadastrado.");
            mostrarMenu();
        }

        Telefone telefone = new Telefone(idInserido, ddd, numero);

        return telefone;
    }

}
