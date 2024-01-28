package utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

import static application.Agenda.mostrarMenu;

public class Contato {
    public static void checarContatoExistente(entities.Contato contato, Long id) {
        boolean idEncontrado = contato.getContatoPorId(id);

        if (!idEncontrado) {
            System.out.println("\n-----Contato não encontrado-----\n");
            mostrarMenu();
        }
    }

    public static Long getIdInserido() {
        Scanner scanner = new Scanner(System.in);
        Long id = Long.parseLong(scanner.nextLine());
        return id;
    }

    public static void escreverDados(BufferedWriter bufferedWriter, String linhaContato) {
        try {
            bufferedWriter.write(linhaContato);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
