package model;

public class Cliente {
    private int idCliente;
    private String nomeCliente;
    private int rg;
    private String cpf;
    private String tipoCliente;

    // construtor sem argumentos
    public Cliente() {
        this.idCliente = 0;
        this.nomeCliente = "";
        this.rg = 0;
        this.cpf = "";
        this.tipoCliente = "";
    }

    // construtor com argumentos
    public Cliente(int idCliente, String nomeCliente, int rg, String cpf, String tipoCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.rg = rg;
        this.cpf = cpf;
        this.tipoCliente = tipoCliente;
    }

    // getters e setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}