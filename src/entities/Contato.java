package entities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Contato {

    private Long id;
    private static List<Telefone> telefones = new ArrayList<>();
    private static File fileContatos = new File("src/contatos.txt");
    private static File fileTelefones = new File("src/telefones.txt");
    private String nome;
    private String sobrenome;


    public Contato() {
        List<String> contatos = getContatos();

        this.id = (contatos.size() > 0) ? Long.parseLong(contatos.get(contatos.size() - 1).split("-")[0].replace("C", "")) + 1 : 1L;
    }

    public void adicionarContatoETelefone(Telefone telefone) {
        addContato();
        adicionarNovoTelefone(telefone);
    }

    public void adicionarNovoTelefone(Telefone telefone) {
        try {
            List<String> telefones = getTelefones();
            FileWriter filewriter = new FileWriter(fileTelefones, true);
            BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
            Long idTelefone = (telefones.size() > 0) ? Long.parseLong(telefones.get(telefones.size() - 1).split("-")[1].replace("T", "")) + 1 : 1;

            String linhaTelefone = String.format("C%s-T%s-%s-%s", this.id, idTelefone.toString(), telefone.getDdd(), telefone.getNumero());
            escreverDados(bufferedWriter, linhaTelefone);
            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addContato() {
        try {
            FileWriter fileWriterContatos = new FileWriter(fileContatos, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterContatos);
            String linhaContato = String.format("%s-%s-%s", this.id.toString(), this.nome, this.sobrenome);
            escreverDados(bufferedWriter, linhaContato);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void escreverDados(BufferedWriter bufferedWriter, String linhaTelefone) {
        try {
            bufferedWriter.write(linhaTelefone);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            arquivo.close();
            return telefones;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTelefone(Telefone telefone) {
        telefones.add(telefone);
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Long getId() {
        return id;
    }

    public void editarContato(Long id, Telefone telefone) {
    }

    public void editarContato(Long id, Telefone telefone, Long idTelefone) {
    }

    public void removerContato(Long idContato) {
        try {
            List<String> contatos = getContatos();

            FileWriter fileWriterContatos = new FileWriter(fileContatos, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriterContatos);

            contatos.stream()
                    .filter(line -> Long.parseLong(line.split("-")[0]) != idContato)
                    .forEach(line -> {
                        String linhaContato = String.format("%s-%s-%s", line.split("-")[0], line.split("-")[1], line.split("-")[2]);
                        escreverDados(bufferedWriter, linhaContato);
                    });

            bufferedWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
