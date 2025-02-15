package br.com.clinica.entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prescricao {
    private LocalDate dataValidade;
    private List<Exame> exames = new ArrayList<>();
    private List<Medicamento> medicamentos = new ArrayList<>();

    public Prescricao(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void adicionarExame(Exame exame) { exames.add(exame); }
    public void removerExame(Exame exame) { exames.remove(exame); }
    public void adicionarMedicamento(Medicamento medicamento) { medicamentos.add(medicamento); }
    public void removerMedicamento(Medicamento medicamento) { medicamentos.remove(medicamento); }

    // Getters
    public LocalDate getDataValidade() { return dataValidade; }
    public List<Exame> getExames() { return exames; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
}