package utilities;

import java.util.List;

public class Telefone {
    public static String obterProximoIdTelefone(List<String> telefones) {
        long maxId = telefones.stream()
                .map(line -> Long.parseLong(line.split("-")[1].replace("T", "")))
                .max(Long::compareTo)
                .orElse(0L);
        return String.valueOf(maxId + 1);
    }
}
