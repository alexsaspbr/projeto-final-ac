import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class GerenciadorDeArquivos {
    // Métodos para ler e escrever contatos e telefones em arquivos

    public void carregarContatosDeArquivo(String caminhoArquivo, Agenda agenda) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;

        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split(",");
            long id = Long.parseLong(partes[0]);
            String nome = partes[1];
            String sobrenome = partes[2];

            Contato contato = new Contato();
            contato.setId(id);
            contato.setNome(nome);
            contato.setSobreNome(sobrenome);
            // Adicionar o contato à agenda
            agenda.adicionarContato(contato);
        }
        leitor.close();
    }

    public void carregarTelefonesDeArquivo(String caminhoArquivo, Agenda agenda) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo));
        String linha;

        while ((linha = leitor.readLine()) != null) {
            String[] partes = linha.split(",");
            long idContato = Long.parseLong(partes[0]);
            String ddd = partes[1];
            long numero = Long.parseLong(partes[2]);

            Telefone telefone = new Telefone();
            telefone.setDdd(ddd);
            telefone.setNumero(numero);

            // Adicionar o telefone ao contato correspondente na agenda
            Contato contato = agenda.getContatoPorId(idContato);
            if (contato != null) {
                contato.getTelefones().add(telefone);
            }
        }
        leitor.close();
    }
}