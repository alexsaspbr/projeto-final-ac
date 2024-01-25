import java.util.*;
public class Agenda {
    private Map<Long, Contato> contatos;
    private Long ultimoId = 0L;

    public Agenda() {
        contatos = new HashMap<>();
    }

    public Long adicionarContato(Contato contato) {
        try {
            contato.setId(++ultimoId);
            contatos.put(contato.getId(), contato);
            return contato.getId();
        } catch (Exception e) {
            System.err.println("Erro ao adicionar contato: " + e.getMessage());
            return null; // Retorna null em caso de falha
        }
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
