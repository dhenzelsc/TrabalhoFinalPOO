package br.com.clinica.entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.clinica.enums.StatusConsulta;

public class Consulta extends Procedimento {
    private LocalTime horarioInicio;
    private int duracao;
    private StatusConsulta status;
    private Paciente paciente;
    private Medico medico;
    private String especialidadeRequerida;
    private Prescricao prescricao;

    public Consulta(double valor, LocalDate data, LocalTime horarioInicio, int duracao,
                    Paciente paciente, Medico medico, String especialidadeRequerida) {
        super(valor, data);
        this.horarioInicio = horarioInicio;
        this.duracao = duracao;
        this.status = StatusConsulta.AGENDADA;
        this.paciente = paciente;
        this.medico = medico;
        this.especialidadeRequerida = especialidadeRequerida;
        this.prescricao = new Prescricao(data.plusDays(7));
    }

    // Getters e Setters
    public LocalTime getHorarioInicio() { return horarioInicio; }
    public int getDuracao() { return duracao; }
    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public String getEspecialidadeRequerida() { return especialidadeRequerida; }
    public Prescricao getPrescricao() { return prescricao; }
}