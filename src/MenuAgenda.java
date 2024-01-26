import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuAgenda {
    private Agenda agenda;
    private Scanner scanner;

    public MenuAgenda() {
        agenda = new Agenda();
        scanner = new Scanner(System.in);
    }

    // todo
    // Aqui você pode listar os contatos
    //agenda.listarContatos().forEach(contato -> System.out.println(contato.getId() + " | " + contato.getNome()));

    public void exibirMenu() {
        while (true) {
            System.out.println(">>>> Menu <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Remover Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            int escolha;
            try {
                escolha = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, insira um número válido.");
                continue;
            }

            processarEscolha(escolha);
        }
    }

    private void processarEscolha(int escolha) {
        scanner.nextLine();

        switch (escolha) {
            case 1:
                adicionarContato();
                break;
            case 2:
                removerContato();
                break;
            case 3:
                editarContato();
                break;
            case 4:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void adicionarContato() {
        System.out.println(">>>> Adicionar Contato <<<<");

        String nome = validacaoNome("Digite o nome: ");

        String sobrenome = validacaoNome("Digite o sobrenome: ");

        // Lógica para adicionar telefone
        List<Telefone> telefones = new ArrayList<>();
        boolean adicionarMais = true;
        while (adicionarMais) {
            Telefone telefone = new Telefone();

            // adiciona o DDD e verifica se é valido: apenas numeros com 2 algarismos
            telefone.setDdd(validarEntradaTelefone("DDD", 2, 2));

            // adiciona o numero e verifica se é valido: apenas numeros entre 8 e 9 algarismos
            telefone.setNumero(Long.parseLong(validarEntradaTelefone("Número de telefone", 8, 9)));

            telefones.add(telefone);

            System.out.print("Deseja adicionar mais um telefone? (s/n): ");
            adicionarMais = scanner.nextLine().trim().equalsIgnoreCase("s");
        }

        Contato novoContato = new Contato();
        novoContato.setNome(nome);
        novoContato.setSobreNome(sobrenome);
        // Definir telefones, se aplicável

        agenda.adicionarContato(novoContato);
        System.out.println("Contato adicionado ID:" + novoContato.getId());
    }



    private void removerContato() {
        // Implementar lógica para remover contato
    }

    private void editarContato() {
        // Implementar lógica para editar contato
    }

    // Outros métodos auxiliares
    private String validacaoNome(String mensagem) {
        String entrada;
        do {
            System.out.print(mensagem);
            entrada = scanner.nextLine();
            if (!entrada.matches("^[A-Za-z]+$") || entrada.length() <= 1) {
                System.out.println("Entrada inválida. Por favor, use apenas letras e mais de um caractere.");
            }
        } while (!entrada.matches("^[A-Za-z]+$") || entrada.length() <= 1);

        return entrada;
    }

    private String validarEntradaTelefone (String tipo, int minLen, int maxLen) {
        String entrada;
        do {
            System.out.print("Digite o " + tipo + ": ");
            entrada = scanner.nextLine();
            if (!entrada.matches("\\d{" + minLen + "," + maxLen + "}")) {
                System.out.println("Entrada inválida. Por favor, insira apenas números com " + minLen + " a " + maxLen + " dígitos.");
            }
        } while (!entrada.matches("\\d{" + minLen + "," + maxLen + "}"));

        return entrada;
    }

}
