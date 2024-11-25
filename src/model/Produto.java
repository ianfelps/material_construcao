package model;

public class Produto {
    // atributos da TB_PRODUTO
    private int idProduto;
    private int idLoja;
    private String nomeProduto;
    private int quantidadeProduto;
    private double valorUnitario;

    // construtor sem argumentos
    public Produto() {
        this.idProduto = 0;
        this.idLoja = 0;
        this.nomeProduto = "";
        this.quantidadeProduto = 0;
        this.valorUnitario = 0;
    }

    // construtor com argumentos
    public Produto(int idProduto, int idLoja, String nomeProduto, int quantidadeProduto, double valorUnitario) {
        this.idProduto = idProduto;
        this.idLoja = idLoja;
        this.nomeProduto = nomeProduto;
        this.quantidadeProduto = quantidadeProduto;
        this.valorUnitario = valorUnitario;
    }

    // getters e setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}