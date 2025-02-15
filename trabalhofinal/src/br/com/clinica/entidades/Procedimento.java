package br.com.clinica.entidades;

import java.time.LocalDate;

public abstract class Procedimento {
    private double valor;
    private boolean pago;
    private LocalDate data;

    public Procedimento(double valor, LocalDate data) {
        this.valor = valor;
        this.data = data;
        this.pago = false;
    }

    // getters
    public void marcarComoPago() { this.pago = true; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public boolean isPago() { return pago; }
}