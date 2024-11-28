package model;

import java.time.LocalDateTime;

public class Venda {
    // atributos da TB_VENDA
    private int idVenda;
    private int idCliente;
    private LocalDateTime dataPagamento;
    private double valorTotalVenda;
    private boolean statusPago;

    // Construtor vazio
    public Venda() {
        this.idVenda = 0;
        this.idCliente = 0;
        this.dataPagamento = null;
        this.valorTotalVenda = 0;
        this.statusPago = false;
    }

    // Construtor com argumentos
    public Venda(int idVenda, int idCliente, LocalDateTime dataHoraPagamento, double valorTotalVenda, boolean statusPago) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.dataPagamento = dataHoraPagamento;
        this.valorTotalVenda = valorTotalVenda;
        this.statusPago = statusPago;
    }

    // Getters e Setters
    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorTotalVenda() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(double valorTotalVenda) {
        this.valorTotalVenda = valorTotalVenda;
    }

    public boolean isStatusPago() {
        return statusPago;
    }

    public void setStatusPago(boolean statusPago) {
        this.statusPago = statusPago;
    }
}