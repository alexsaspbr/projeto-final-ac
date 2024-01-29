import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        Agenda agenda = new Agenda();
        Scanner scanner = new Scanner(System.in);

        System.out.println("##################\n" +
                "##### AGENDA #####\n" +
                "##################");

        MenuAgenda menuAgenda = new MenuAgenda();

        menuAgenda.exibirMenu();

    }

}