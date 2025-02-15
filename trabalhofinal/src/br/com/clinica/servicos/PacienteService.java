package br.com.clinica.servicos;

import br.com.clinica.entidades.Paciente;
import br.com.clinica.util.ValidacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class PacienteService {
    private final List<Paciente> pacientes = new ArrayList<>();

    public void cadastrar(Paciente paciente) throws IllegalArgumentException {
        if (!ValidacaoUtil.validarCPF(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        
        if (!ValidacaoUtil.validarNome(paciente.getNome())) {
            throw new IllegalArgumentException("Nome inválido!");
        }
        
        if (buscarPorCpf(paciente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado!");
        }
        
        pacientes.add(paciente);
    }

    public Paciente buscarPorCpf(String cpf) {
        return pacientes.stream()
            .filter(p -> p.getCpf().equals(cpf))
            .findFirst()
            .orElse(null);
    }

    public List<Paciente> listarTodos() {
        return pacientes;
    }
}