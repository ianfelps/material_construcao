package model;

public class ProdutoVenda {
    private int idProdutoVenda;
    private int idVenda;
    private int codigoProduto;
    private int quantidadeProdutoRetirado;
    private double valorTotalProduto;

    // construtor vazio
    public ProdutoVenda() {
        this.idProdutoVenda = 0;
        this.idVenda = 0;
        this.codigoProduto = 0;
        this.quantidadeProdutoRetirado = 0;
        this.valorTotalProduto = 0;
    }

    // construtor com argumentos
    public ProdutoVenda(int idProdutosVenda, int idVenda, int codigoProduto, int quantidadeProdutoRetirado, double valorTotalProduto) {
        this.idProdutoVenda = idProdutosVenda;
        this.idVenda = idVenda;
        this.codigoProduto = codigoProduto;
        this.quantidadeProdutoRetirado = quantidadeProdutoRetirado;
        this.valorTotalProduto = valorTotalProduto;
    }

    // getters e setters
    public int getIdProdutoVenda() {
        return idProdutoVenda;
    }

    public void setIdProdutoVenda(int idProdutosVenda) {
        this.idProdutoVenda = idProdutosVenda;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public int getQuantidadeProdutoRetirado() {
        return quantidadeProdutoRetirado;
    }

    public void setQuantidadeProdutoRetirado(int quantidadeProdutoRetirado) {
        this.quantidadeProdutoRetirado = quantidadeProdutoRetirado;
    }

    public double getValorTotalProduto() {
        return valorTotalProduto;
    }

    public void setValorTotalProduto(double valorTotalProduto) {
        this.valorTotalProduto = valorTotalProduto;
    }
}
