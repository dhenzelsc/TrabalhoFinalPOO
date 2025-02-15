package br.com.clinica.servicos;

import br.com.clinica.entidades.Medico;
import br.com.clinica.util.ValidacaoUtil;

import java.util.ArrayList;
import java.util.List;

public class MedicoService {
    private final List<Medico> medicos = new ArrayList<>();

    public void cadastrar(Medico medico) throws IllegalArgumentException {
        if (!ValidacaoUtil.validarCRM(medico.getCrm())) {
            throw new IllegalArgumentException("CRM inválido!");
        }
        
        if (!ValidacaoUtil.validarNome(medico.getNome())) {
            throw new IllegalArgumentException("Nome inválido!");
        }
        
        if (buscarPorCrm(medico.getCrm()) != null) {
            throw new IllegalArgumentException("CRM já cadastrado!");
        }
        
        medicos.add(medico);
    }

    public Medico buscarPorCrm(String crm) {
        return medicos.stream()
            .filter(m -> m.getCrm().equals(crm))
            .findFirst()
            .orElse(null);
    }

    public List<Medico> listarTodos() {
        return medicos;
    }
}