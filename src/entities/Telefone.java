package entities;

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

    public Long getId() {
        return this.id;
    }

    public String getTelefone() {
        return this.ddd + this.numero.toString();
    }
}
