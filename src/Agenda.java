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


    public boolean removerContato(Long id) {
        if (contatos.containsKey(id)) {
            contatos.remove(id);
            return true; // Contato removido com sucesso
        }
        return false; // Contato não encontrado
    }

    public boolean editarContato(Long id, Contato contatoAtualizado) {
        if (contatos.containsKey(id)) {
            contatos.put(id, contatoAtualizado);
            return true; // Contato atualizado com sucesso
        }
        return false; // Contato não encontrado
    }

    public List<Contato> listarContatos() {
        // Retorna lista de contatos
        return new ArrayList<>(contatos.values());
    }

    // Métodos auxiliares...

    public boolean existeContato(String nome, String sobrenome) {
        for (Contato contato : contatos.values()) {
            if (contato.getNome().equalsIgnoreCase(nome) && contato.getSobreNome().equalsIgnoreCase(sobrenome)) {
                return true;
            }
        }
        return false;
    }

    public boolean telefoneExiste(Telefone telefone) {
        for (Contato contato : contatos.values()) {
            List<Telefone> telefonesDoContato = contato.getTelefones();
            if (telefonesDoContato != null) {
                for (Telefone tel : telefonesDoContato) {
                    if (tel.getNumero().equals(telefone.getNumero())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Map<Long, Contato> getContatos() {
        return contatos;
    }

    public Contato getContatoPorId(long id) {
        return contatos.get(id);
    }

    // método para pegar todos os contatos utilizado pelos metodos de escrita
    public List<Contato> getTodosOsContatos() {
        return new ArrayList<>(contatos.values());
    }

}
