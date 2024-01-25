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

    public void setId(Long id) {
        this.id = id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getId() {
        return this.id;
    }

    public String getTelefone() {
        return this.ddd + this.numero.toString();
    }
}
