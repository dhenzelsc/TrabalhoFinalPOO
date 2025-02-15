package br.com.clinica.entidades;

import java.time.LocalDate;

public class Exame extends Procedimento {
    private String tipo;
    private LocalDate dataRealizacao;
    private String resultado;

    public Exame(String tipo, double valor, LocalDate dataPrescricao) {
        super(valor, dataPrescricao);
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getTipo() { return tipo; }
    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public void setDataRealizacao(LocalDate data) { this.dataRealizacao = data; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
}