package br.com.clinica.entidades;

import java.time.LocalDate;

public class Medico extends Pessoa {
    private String crm;
    private String especialidade;

    public Medico(String nome, String cpf, LocalDate dataNascimento, String crm, String especialidade) {
        super(nome, cpf, dataNascimento);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm() { return crm; }
    public String getEspecialidade() { return especialidade; }
}