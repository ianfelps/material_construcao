package model;

public class EnderecoCliente {
    // atributos da TB_ENDERECO_CLIENTE
    private int idEnderecoCliente;
    private int idCliente;
    private String siglaUF;
    private String nomeEndereco;
    private String nomeCidade;

    // construtor sem argumentos
    public EnderecoCliente() {
        idEnderecoCliente = 0;
        idCliente = 0;
        siglaUF = "";
        nomeEndereco = "";
        nomeCidade = "";
    }

    // construtor com argumentos
    public EnderecoCliente(int idEnderecoCliente, int idCliente, String siglaUF, String nomeEndereco, String nomeCidade) {
        this.idEnderecoCliente = idEnderecoCliente;
        this.idCliente = idCliente;
        this.siglaUF = siglaUF;
        this.nomeEndereco = nomeEndereco;
        this.nomeCidade = nomeCidade;
    }

    // getters e setters
    public int getIdEnderecoCliente() {
        return idEnderecoCliente;
    }

    public void setIdEnderecoCliente(int idEnderecoCliente) {
        this.idEnderecoCliente = idEnderecoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getSiglaUF() {
        return siglaUF;
    }

    public void setSiglaUF(String siglaUF) {
        this.siglaUF = siglaUF;
    }

    public String getNomeEndereco() {
        return nomeEndereco;
    }

    public void setNomeEndereco(String nomeEndereco) {
        this.nomeEndereco = nomeEndereco;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }
}
