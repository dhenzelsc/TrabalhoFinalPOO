package br.com.clinica.entidades;

public class Medicamento {
    private String nome;
    private String dosagem;
    private int duracaoTratamento;

    public Medicamento(String nome, String dosagem, int duracao) {
        this.nome = nome;
        this.dosagem = dosagem;
        this.duracaoTratamento = duracao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDosagem() { return dosagem; }
    public int getDuracaoTratamento() { return duracaoTratamento; }
}