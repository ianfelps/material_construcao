package model;

import java.time.LocalDateTime;

public class Venda {
    // atributos da TB_VENDA
    private int idVenda;
    private int idCliente;
    private LocalDateTime dataHoraPagamento;
    private double valorTotalVenda;
    private boolean statusPago;

    // Construtor vazio
    public Venda() {
        this.idVenda = 0;
        this.idCliente = 0;
        this.dataHoraPagamento = null;
        this.valorTotalVenda = 0;
        this.statusPago = false;
    }

    // Construtor com argumentos
    public Venda(int idVenda, int idCliente, LocalDateTime dataHoraPagamento, double valorTotalVenda, boolean statusPago) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.dataHoraPagamento = dataHoraPagamento;
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

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    public void setDataHoraPagamento(LocalDateTime dataHoraPagamento) {
        this.dataHoraPagamento = dataHoraPagamento;
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