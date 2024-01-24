import java.util.*;
public class Agenda {
    private Map<Long, Contato> contatos;
    private Long ultimoId = 0L;

    public Agenda() {
        contatos = new HashMap<>();
    }

    public void adicionarContato(Contato contato) {
        // Implementar verificação de ID único e adição
    }

    public void removerContato(Long id) {
        // Implementar remoção
    }

    public void editarContato(Long id, Contato novoContato) {
        // Implementar edição
    }

    public List<Contato> listarContatos() {
        // Retorna lista de contatos
        return null;
    }

    // Métodos auxiliares...
}
