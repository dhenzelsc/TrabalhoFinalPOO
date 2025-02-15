package br.com.clinica.servicos;

import java.time.LocalDate;

import br.com.clinica.entidades.*;

public class PrescricaoService {
    public void prescreverExame(Prescricao prescricao, Exame exame, LocalDate data) {
        exame.setDataRealizacao(data);
        prescricao.adicionarExame(exame);
    }

    public void prescreverMedicamento(Prescricao prescricao, Medicamento medicamento) {
        prescricao.adicionarMedicamento(medicamento);
    }

    public void exibirPrescricao(Prescricao prescricao) {
        System.out.println("\n--- Prescrição ---");
        System.out.println("Validade: " + prescricao.getDataValidade());
        
        System.out.println("\nExames:");
        prescricao.getExames().forEach(e -> 
            System.out.printf("• %s - %s%n", e.getTipo(), e.getDataRealizacao()));
        
        System.out.println("\nMedicamentos:");
        prescricao.getMedicamentos().forEach(m -> 
            System.out.printf("• %s (%s) - %d dias%n", 
                m.getNome(), m.getDosagem(), m.getDuracaoTratamento()));
    }
}