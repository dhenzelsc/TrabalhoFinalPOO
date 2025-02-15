package br.com.clinica.servicos;

import br.com.clinica.entidades.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatalogoService {
    private List<Exame> examesDisponiveis = new ArrayList<>();
    private List<Medicamento> medicamentosDisponiveis = new ArrayList<>();

    public CatalogoService() {
        examesDisponiveis.add(new Exame("Sangue", 150.0, LocalDate.now()));
        examesDisponiveis.add(new Exame("Raio-X", 300.0, LocalDate.now()));
        
        medicamentosDisponiveis.add(new Medicamento("Paracetamol", "500mg", 7));
        medicamentosDisponiveis.add(new Medicamento("Amoxicilina", "250mg", 10));
    }

    public List<Exame> getExamesDisponiveis() { return examesDisponiveis; }
    public List<Medicamento> getMedicamentosDisponiveis() { return medicamentosDisponiveis; }
}