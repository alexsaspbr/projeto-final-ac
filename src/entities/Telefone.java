package entities;

import java.util.List;

public class Telefone {

    private Long id;
    private String ddd;
    private String numero;

    public Telefone(Long id, String ddd, String numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
    }

    public String getDdd() {
        return ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setId(List<String> telefones) {
        this.id = (telefones.size() > 0) ? Long.parseLong(telefones.get(telefones.size() - 1).split("-")[1].replace("T", "")) + 1 : 1;
    }

    public Long getId() {
        return this.id;
    }
}
