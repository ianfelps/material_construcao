package model;

public class TelefoneCliente {
    // atributos da TB_TELEFONE_CLIENTE
    private int idTelefoneCliente;
    private int idCliente;
    private String numeroTelefone;

    // construtor sem argumentos
    public TelefoneCliente() {
        this.idTelefoneCliente = 0;
        this.idCliente = 0;
        this.numeroTelefone = "";
    }

    // construtor com argumentos
    public TelefoneCliente(int idTelefoneCliente, int idCliente, String numeroTelefone) {
        this.idTelefoneCliente = idTelefoneCliente;
        this.idCliente = idCliente;
        this.numeroTelefone = numeroTelefone;
    }

    // getters e setters
    public int getIdTelefoneCliente() {
        return idTelefoneCliente;
    }

    public void setIdTelefoneCliente(int idTelefoneCliente) {
        this.idTelefoneCliente = idTelefoneCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }
}