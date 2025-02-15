package br.com.clinica.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<Procedimento> historico = new ArrayList<>();

    public Pessoa(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    // Getters e métodos
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void adicionarProcedimento(Procedimento p) { historico.add(p); }
    public List<Procedimento> getHistorico() { return historico; }
}