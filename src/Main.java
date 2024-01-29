import br.com.ada.tech.model.Contato;
import br.com.ada.tech.model.Telefone;
import br.com.ada.tech.repository.ContatoRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static ContatoRepository contatoRepository = new ContatoRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    adicionarContato();
                    break;
                case 2:
                    removerContato();
                    break;
                case 3:
                    editarContato();
                    break;
                case 4 :
                    exibirTodosContatos();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
    }

    private static void exibirMenu() {
        System.out.println("##################");
        System.out.println("##### AGENDA #####");
        System.out.println("##################");
        System.out.println(">>>> Contatos <<<<");
        System.out.println("Id | Nome");
        listarContatos();
        System.out.println(">>>> Menu <<<<");
        System.out.println("1 - Adicionar Contato");
        System.out.println("2 - Remover Contato");
        System.out.println("3 - Editar Contato");
        System.out.println("4 - Exibir Todos Contatos");
        System.out.println("5 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void listarContatos() {
        contatoRepository.todos().forEach(contato -> {
            System.out.println(contato.getId() + " | " + contato.getNome());
        });
    }

    private static void adicionarContato() {
        Contato novoContato = new Contato();
        System.out.print("Informe o nome do contato: ");
        String nome = scanner.nextLine();
        novoContato.setNome(nome);

        // Adicionando telefones
        String ddd = "";
        boolean adicionarTelefone = true;
        while (adicionarTelefone) {
            System.out.print("Informe o DDD do telefone (dois dígitos): ");
            ddd = scanner.nextLine();
            if (ddd.length() != 2 || !ddd.matches("\\d+")) {
                System.out.println("DDD inválido. O DDD deve conter exatamente dois dígitos numéricos.");
                continue;
            }

            System.out.print("Informe o número do telefone: ");
            String numeroTelefone = scanner.nextLine();

            // Verifica se o número já está registrado
            boolean numeroRegistrado = verificarNumeroRegistrado(numeroTelefone);
            if (numeroRegistrado) {
                System.out.println("Este número de telefone já está registrado.");
                System.out.println("Informe o DDD do telefome (dois digitos) ou digite 'S' para sair: ");
                String opcao = scanner.nextLine();
                if (opcao.equalsIgnoreCase("S")) {
                    break; // Sai do loop de adicionar telefone
                } else {
                    // Continua para a próxima iteração do loop
                    continue;
                }
            }

            if (!numeroTelefone.matches("\\d{8,12}")) {
                System.out.println("Número de telefone inválido. Certifique-se de inserir apenas números de 8 a 12 dígitos.");
                continue;
            }

            Long numero = Long.parseLong(numeroTelefone);

            Telefone telefone = new Telefone();
            telefone.setDdd(ddd);
            telefone.setNumero(numero);
            novoContato.getTelefones().add(telefone);

            System.out.print("Deseja adicionar outro telefone? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();
            if (!resposta.equals("S")) {
                adicionarTelefone = false;
            }
        }

        contatoRepository.salvar(novoContato);
        System.out.println("Contato adicionado com sucesso!");
    }


    private static boolean verificarNumeroRegistrado(String numeroTelefone) {
        for (Contato contato : contatoRepository.todos()) {
            for (Telefone telefone : contato.getTelefones()) {
                if (telefone.getNumero().toString().equals(numeroTelefone)) {
                    return true; // Número já registrado
                }
            }
        }
        return false; // Número não registrado
    }


    private static void removerContato() {
        System.out.print("Informe o ID do contato que deseja remover: ");
        Long id = scanner.nextLong();
        contatoRepository.remover(id);
        System.out.println("Contato removido com sucesso!");
    }

    private static void editarContato() {
        boolean continuarEdicao = true;
        do {
            System.out.print("Informe o ID do contato que deseja editar: ");
            Long id = scanner.nextLong();
            scanner.nextLine(); // Limpar o buffer do scanner

            // Verificar se o contato com o ID fornecido existe
            Contato contato = contatoRepository.buscarPorId(id);
            if (contato == null) {
                System.out.println("Contato não encontrado. Verifique o ID digitado.");
                continue; // Volta ao início do loop para solicitar novamente o ID
            }

            System.out.println("Contato selecionado: " + contato.getNome());
            System.out.println("1 - Editar nome");
            System.out.println("2 - Visualizar e editar telefones");
            System.out.println("3 - Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    System.out.print("Informe o novo nome do contato: ");
                    String novoNome = scanner.nextLine();
                    contato.setNome(novoNome);
                    contatoRepository.atualizar(contato);
                    System.out.println("Contato atualizado com sucesso!");
                    break;
                case 2:
                    visualizarEditarTelefones(contato);
                    break;
                case 3:
                    System.out.println("Voltando ao menu principal...");
                    continuarEdicao = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            if (continuarEdicao) {
                System.out.println("Deseja fazer outra alteração no contato? (S/N)");
                String continuar = scanner.nextLine().toUpperCase();
                if (!continuar.equals("S")) {
                    continuarEdicao = false;
                }
            }
        } while (continuarEdicao);
    }



    private static void visualizarEditarTelefones(Contato contato) {
        List<Telefone> telefones = contato.getTelefones();
        System.out.println("Telefones cadastrados:");
        for (int i = 0; i < telefones.size(); i++) {
            Telefone telefone = telefones.get(i);
            System.out.println((i + 1) + " - " + telefone.getDdd() + "-" + telefone.getNumero());
        }
        System.out.println("Digite o número do telefone que deseja editar ou excluir.");
        System.out.println("Digite 0 para voltar ao menu anterior.");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        if (escolha == 0) {
            return;
        } else if (escolha > 0 && escolha <= telefones.size()) {
            // Opção válida, editar ou excluir o telefone
            Telefone telefoneSelecionado = telefones.get(escolha - 1);
            System.out.println("Deseja editar (E) ou excluir (X) o telefone selecionado?");
            String acao = scanner.nextLine().toUpperCase();
            if (acao.equals("E")) {
                // Implemente a lógica para editar o telefone
                System.out.println("Informe o novo DDD e número do telefone:");
                String novoDDD = scanner.nextLine();
                String novoNumero = scanner.nextLine();
                telefoneSelecionado.setDdd(novoDDD);
                telefoneSelecionado.setNumero(Long.parseLong(novoNumero));
                System.out.println("Telefone editado com sucesso!");
            } else if (acao.equals("X")) {
                // Implemente a lógica para excluir o telefone
                telefones.remove(telefoneSelecionado);
                System.out.println("Telefone excluído com sucesso!");
            } else {
                System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }


    private static void exibirTodosContatos() {
        List<Contato> contatos = contatoRepository.todos();
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            System.out.println("Lista de Contatos:");
            System.out.println("ID | Nome | Telefones");
            for (Contato contato : contatos) {
                StringBuilder telefones = new StringBuilder();
                for (Telefone telefone : contato.getTelefones()) {
                    telefones.append(telefone.getDdd()).append("-").append(telefone.getNumero()).append(" | ");
                }
                System.out.println(contato.getId() + " | " + contato.getNome() + " | " + telefones.toString());
            }
        }
    }



}
