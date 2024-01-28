package entities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utilities.Contato.escreverDados;
import static utilities.Telefone.obterProximoIdTelefone;

public class Contato {

    private static final File fileContatos = new File("src/contatos.txt");
    private static final File fileTelefones = new File("src/telefones.txt");
    private static List<Telefone> telefones = new ArrayList<>();
    private Long id;
    private String nome;
    private String sobrenome;

    public Contato() {
    }

    public static List<String> getContatos() {
        try {
            FileReader arquivo = new FileReader(fileContatos);
            BufferedReader bufferedReader = new BufferedReader(arquivo);

            String line;
            List<String> contatos = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                contatos.add(line);
            }
            bufferedReader.close();

            return contatos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getTelefones() {
        try {
            FileReader arquivo = new FileReader(fileTelefones);
            BufferedReader bufferedReader = new BufferedReader(arquivo);
            String line;
            List<String> telefones = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                telefones.add(line);
            }
            bufferedReader.close();

            return telefones;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void adicionarNovoTelefone(Telefone telefone, Long id) {
        try {
            List<String> telefones = getTelefones();
            FileWriter filewriter = new FileWriter(fileTelefones, true);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            telefone.setId(telefones);

            String linhaTelefone = String.format("C%s-T%s-%s-%s", id, telefone.getId(), telefone.getDdd(), telefone.getNumero());
            escreverDados(bufferedWriter, linhaTelefone);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public void setTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public boolean getContatoPorId(Long idContato) {
        List<String> contatos = getContatos();
        boolean contatoEncontrado = contatos.stream().anyMatch(line -> Long.parseLong(line.split("-")[0]) == idContato);
        return contatoEncontrado;
    }

    public void adicionarContatoETelefone(Telefone telefone) {
        adicionarContato();
        adicionarNovoTelefone(telefone, this.id);
    }

    private void adicionarContato() {
        try {
            List<String> contatos = getContatos();
            this.id = (contatos.size() > 0) ? Long.parseLong(contatos.get(contatos.size() - 1).split("-")[0]) + 1 : 1L;

            FileWriter fileWriterContatos = new FileWriter(fileContatos, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterContatos);
            String linhaContato = String.format("%s-%s-%s", this.id.toString(), this.nome, this.sobrenome);
            escreverDados(bufferedWriter, linhaContato);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int editarContato(Long idContato, Telefone telefone) {
        try {
            List<String> contatos = getContatos();
            List<String> telefones = getTelefones();

            FileWriter fileWriterContatos = new FileWriter(fileContatos, false);
            FileWriter fileWriterTelefones = new FileWriter(fileTelefones, false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterContatos);
            BufferedWriter bufferedTelefones = new BufferedWriter(fileWriterTelefones);

            for (int i = 0; i < contatos.size(); i++) {
                if (Long.parseLong(contatos.get(i).split("-")[0]) == idContato) {
                    contatos.set(i, String.format("%d-%s-%s", idContato, this.nome, this.sobrenome));
                    break;
                }
            }

            contatos.forEach(line -> {
                escreverDados(bufferedWriter, line);
            });
            bufferedWriter.close();

            telefones.removeIf(line -> Long.parseLong(line.split("-")[0].replace("C", "")) == idContato);
            String proximoIdTelefone = obterProximoIdTelefone(telefones);

            String novaLinhaTelefone = String.format("C%d-T%s-%s-%s", idContato, proximoIdTelefone, telefone.getDdd(), telefone.getNumero());
            telefones.add(novaLinhaTelefone);

            telefones.forEach(line -> {
                escreverDados(bufferedTelefones, line);
            });
            bufferedTelefones.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void removerContato(Long idContato) {
        try {
            List<String> contatos = getContatos();
            List<String> telefones = getTelefones();

            FileWriter fileWriterContatos = new FileWriter(fileContatos, false);
            FileWriter fileWriterTelefones = new FileWriter(fileTelefones, false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterContatos);
            BufferedWriter bufferedTelefones = new BufferedWriter(fileWriterTelefones);

            contatos.stream().filter(line -> Long.parseLong(line.split("-")[0]) != idContato).forEach(line -> {
                String linhaContato = String.format("%s-%s-%s", line.split("-")[0], line.split("-")[1], line.split("-")[2]);
                escreverDados(bufferedWriter, linhaContato);
            });
            bufferedWriter.close();

            telefones.removeIf(line -> Long.parseLong(line.split("-")[0].replace("C", "")) == idContato);

            telefones.forEach(line -> {
                escreverDados(bufferedTelefones, line);
            });

            bufferedTelefones.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
