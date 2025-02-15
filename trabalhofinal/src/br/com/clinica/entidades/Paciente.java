package br.com.clinica.entidades;

import java.time.LocalDate;

public class Paciente extends Pessoa {
    private boolean pagamentoPendente;

    public Paciente(String nome, String cpf, LocalDate dataNascimento) {
        super(nome, cpf, dataNascimento);
        this.pagamentoPendente = false;
    }

    public boolean temPagamentoPendente() { return pagamentoPendente; }
    public void setPagamentoPendente(boolean status) { this.pagamentoPendente = status; }
}