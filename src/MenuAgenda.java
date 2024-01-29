import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/*
            não ficou muito bem organizado, eu deveria ter criado mais classes
            essa classe acabou saindo do controle, era apenas para ser a classe
            para mostrar os menus na tela, acabou ficando as validações e outros
             métodos necessários também
* */

public class MenuAgenda {

    private Agenda agenda;
    private Scanner scanner;

    public MenuAgenda() {
        agenda = new Agenda();

        // carrega os contatos dos arquivos agenda.txt e telefones.txt
        GerenciadorDeArquivos gerenciador = new GerenciadorDeArquivos();
        try {
            gerenciador.carregarContatosDeArquivo("resources/agenda.txt", agenda);
            gerenciador.carregarTelefonesDeArquivo("resources/telefones.txt", agenda);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        scanner = new Scanner(System.in);
    }



    public void exibirMenu() throws IOException {
        while (true) {
            // chama o método para mostrar os contatos
            List<Contato> contatos = getContatos();

            System.out.println("\n>>>> Menu <<<<");
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

            //caso nao tenha nenhum  contato na agenda e o usuário tente fazer algo além de adicionar um novo contato
            if ((escolha == 2 || escolha == 3) && contatos.isEmpty()) {
                System.out.println("A lista de contatos está vazia.");
                continue;
            }

            processarEscolha(escolha);
        }

    }

    // método para mostrar os contatos: ID | Nome Sobrenome | numeroX1, numeroX2....
    private List<Contato> getContatos() {
        System.out.println("\n>>>> Contatos <<<<");
        System.out.println("Id | Nome | Telefone(s)");
        List<Contato> contatos = agenda.listarContatos();
        for (Contato contato : contatos) {
            System.out.println(contato.getId() + " | " + contato.getNome() + " " + contato.getSobreNome() + " | " + contato.getTelefonesFormatados());
        }
        return contatos;
    }

    private void processarEscolha(int escolha) throws IOException {
                switch (escolha) {
            case 1:
                menuAdicionarContato();
                break;
            case 2:
                menuRemoverContato();
                break;
            case 3:
                menuEditarContato();
                break;
            case 4:
                System.out.println("Saindo...");
                System.exit(0);
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void menuAdicionarContato() throws IOException {
        boolean continuarAdicionarContato = true;

        while (continuarAdicionarContato) {
            System.out.println("\n>>>> Adicionar Contato <<<<");

            String nome = validacaoNome("Digite o nome: ");
            String sobrenome = validacaoNome("Digite o sobrenome: ");

            // validação caso ja exista um contato com exato nome e sobrenome
            if (agenda.existeContato(nome, sobrenome)) {
                System.out.println("Um contato com esse nome e sobrenome já existe.");
                System.out.print("Deseja tentar novamente? (s/n): ");
                String resposta = scanner.nextLine().trim();
                if (!resposta.equalsIgnoreCase("s")) {
                    continuarAdicionarContato = false;
                }
            } else {
                Contato novoContato = new Contato();
                novoContato.setNome(nome);
                novoContato.setSobreNome(sobrenome);

                // cria um loop para caso o usuario queira adicionar mais de um telefone
                boolean adicionarMais = true;
                while (adicionarMais) {
                    Telefone novoTelefone = adicionarNovoTelefone(novoContato);
                    if (novoTelefone == null) {
                        break; // Interrompe o loop se o usuário decidir não tentar adicionar outro número
                    }

                    novoContato.getTelefones().add(novoTelefone);

                    System.out.print("Deseja adicionar mais um telefone? (s/n): ");
                    // caso s adicionarMais continua True...
                    adicionarMais = scanner.nextLine().trim().equalsIgnoreCase("s");
                }

                if (!novoContato.getTelefones().isEmpty()) {
                    agenda.adicionarContato(novoContato);
                    // atualiza os arquivos txt
                    GerenciadorDeArquivos.salvarContatosEmContatosTxt("resources/agenda.txt", agenda);
                    GerenciadorDeArquivos.salvarTelefonesEmTelefonesTxt("resources/telefones.txt", agenda);
                    System.out.println("Contato adicionado com sucesso!");
                }
                continuarAdicionarContato = false;
            }
        }
    }


    private void menuRemoverContato() throws IOException {
        getContatos();
        System.out.print("\nDigite o ID do contato a ser removido (0 para voltar): ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um ID válido.");
            return;
        }

        if (agenda.removerContato(id)) {
            // atualiza os arquivos txt
            GerenciadorDeArquivos.salvarContatosEmContatosTxt("resources/agenda.txt", agenda);
            GerenciadorDeArquivos.salvarTelefonesEmTelefonesTxt("resources/telefones.txt", agenda);
            System.out.println("Contato removido com sucesso.");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }


    private void menuEditarContato() throws IOException {
        getContatos();
        System.out.print("\nDigite o ID do contato a ser editado (0 para voltar): ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um ID válido.");
            return;
        }

        if (!agenda.getContatos().containsKey(id)) {
            System.out.println("Contato não encontrado.");
            return;
        }

        Contato contatoAtual = agenda.getContatos().get(id);
        boolean continuarEdicao = true;

        while (continuarEdicao) {
            System.out.println("\nEditando contato: " + contatoAtual.getNome() + " " + contatoAtual.getSobreNome());
            System.out.println("Escolha uma opção de edição:");
            System.out.println("1 - Editar Nome");
            System.out.println("2 - Editar Sobrenome");
            System.out.println("3 - Editar Telefones");
            System.out.println("4 - Finalizar Edição");
            System.out.print("Opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (escolha) {
                case 1:
                    String novoNome = validacaoNome("Digite o novo nome: ");
                    contatoAtual.setNome(novoNome);
                    GerenciadorDeArquivos.salvarContatosEmContatosTxt("resources/agenda.txt", agenda);
                    break;
                case 2:
                    String novoSobrenome = validacaoNome("Digite o novo sobrenome: ");
                    contatoAtual.setSobreNome(novoSobrenome);
                    GerenciadorDeArquivos.salvarContatosEmContatosTxt("resources/agenda.txt", agenda);
                    break;
                case 3:
                    menuEditarTelefonesContato(contatoAtual);
                    break;
                case 4:
                    continuarEdicao = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        if (agenda.editarContato(id, contatoAtual)) {
            System.out.println("Contato atualizado com sucesso.");
        } else {
            System.out.println("Falha ao atualizar contato.");
        }
    }

    private void menuEditarTelefonesContato(Contato contato) throws IOException {
        boolean continuarEdicao = true;
        while (continuarEdicao) {
            System.out.println("Editando telefones do contato: " + contato.getNome() + " " + contato.getSobreNome());
            System.out.println("1 - Adicionar novo telefone");
            System.out.println("2 - Remover telefone existente");
            System.out.println("3 - Alterar telefone existente");
            System.out.println("4 - Voltar");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();  // Consumir a quebra de linha

            switch (escolha) {
                case 1:
                    Telefone novoTelefone = adicionarNovoTelefone(contato);
                    if (novoTelefone != null) {
                        contato.getTelefones().add(novoTelefone);
                        GerenciadorDeArquivos.salvarTelefonesEmTelefonesTxt("resources/telefones.txt", agenda);
                        System.out.println("Telefone adicionado com sucesso.");
                    }
                    break;
                case 2:
                    removerTelefone(contato);
                    GerenciadorDeArquivos.salvarTelefonesEmTelefonesTxt("resources/telefones.txt", agenda);
                    break;
                case 3:
                    alterarTelefone(contato);
                    GerenciadorDeArquivos.salvarTelefonesEmTelefonesTxt("resources/telefones.txt", agenda);
                    break;
                case 4:
                    continuarEdicao = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    // Métodos de validação
    private String validacaoNome(String mensagem) {
        String entrada;
        do {
            System.out.print(mensagem);
            entrada = scanner.nextLine();
                if (!entrada.matches("^[A-Za-z]+$") || entrada.length() <= 1) {
                System.out.println("\nEntrada inválida. Por favor, use apenas letras e mais de um caractere.");
            }
        } while (!entrada.matches("^[A-Za-z]+$") || entrada.length() <= 1);

        return entrada;
    }

    private Telefone adicionarNovoTelefone(Contato contato) throws IOException {
        System.out.println("Adicionando telefone para: " + contato.getNome() + " " + contato.getSobreNome());

        while (true) {
            String ddd = validarEntradaTelefone("Digite o DDD", 2, 2);
            String numero = validarEntradaTelefone("Digite o número do telefone", 8, 9);

            Telefone novoTelefone = new Telefone();
            novoTelefone.setDdd(ddd);
            novoTelefone.setNumero(Long.parseLong(numero));

            if (contato.getTelefones().stream().anyMatch(t -> t.getNumero().equals(novoTelefone.getNumero())) || agenda.telefoneExiste(novoTelefone)) {
                System.out.println("Este número de telefone já existe.");
                System.out.print("Deseja tentar adicionar outro número? (s/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    return null; // Retorna null para indicar que não deve tentar adicionar mais telefones
                }
            } else {
                return novoTelefone; // Retorna o telefone válido adicionado
            }
        }
    }

    // valida a entrada do telefone
    // DDD precisa ser dois numeros
    // numero precisa ser entre 8 e 9 algarismos
    private String validarEntradaTelefone (String tipo, int minLen, int maxLen) {
        String entrada;
        do {
            System.out.print(tipo + ": ");
            entrada = scanner.nextLine();
            if (!entrada.matches("\\d{" + minLen + "," + maxLen + "}")) {
                System.out.println("Entrada inválida. Por favor, insira apenas números com " + minLen + " a " + maxLen + " dígitos.");
            }
        } while (!entrada.matches("\\d{" + minLen + "," + maxLen + "}"));

        return entrada;
    }



    private void alterarTelefone(Contato contato) {
        if (contato.getTelefones().isEmpty()) {
            System.out.println("Não há telefones para alterar neste contato.");
            return;
        }

        System.out.println("Selecione o telefone para alterar:");
        for (int i = 0; i < contato.getTelefones().size(); i++) {
            Telefone tel = contato.getTelefones().get(i);
            System.out.println((i + 1) + " - DDD: " + tel.getDdd() + ", Número: " + tel.getNumero());
        }

        System.out.print("Escolha um número (0 para voltar):");
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
            return;
        }

        if (escolha >= 0 && escolha < contato.getTelefones().size()) {
            Telefone telefoneSelecionado = contato.getTelefones().get(escolha);

            String novoDdd = validarEntradaTelefone("Digite o novo DDD", 2, 2);
            String novoNumero = validarEntradaTelefone("Digite o novo número do telefone", 8, 9);

            telefoneSelecionado.setDdd(novoDdd);
            telefoneSelecionado.setNumero(Long.parseLong(novoNumero));
            System.out.println("Telefone alterado com sucesso.");
        } else if (escolha != -1) {
            System.out.println("Seleção inválida.");
        }
    }

    private void removerTelefone(Contato contato) {
        if (contato.getTelefones().isEmpty()) {
            System.out.println("Não há telefones para remover neste contato.");
            return;
        }

        System.out.println("Selecione o telefone para remover:");
        for (int i = 0; i < contato.getTelefones().size(); i++) {
            Telefone tel = contato.getTelefones().get(i);
            System.out.println((i + 1) + " - DDD: " + tel.getDdd() + ", Número: " + tel.getNumero());
        }

        System.out.print("Escolha um número (ou 0 para voltar): ");
        int escolha;
        try {
            escolha = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
            return;
        }

        if (escolha >= 0 && escolha < contato.getTelefones().size()) {
            contato.getTelefones().remove(escolha);
            System.out.println("Telefone removido com sucesso.");
        } else if (escolha != -1) {
            System.out.println("Seleção inválida.");
        }
    }

}
