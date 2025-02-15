package Main;

import br.com.clinica.entidades.*;
import br.com.clinica.enums.*;
import br.com.clinica.excecoes.*;
import br.com.clinica.servicos.*;
import br.com.clinica.util.ValidacaoUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PacienteService pacienteService = new PacienteService();
    private static final MedicoService medicoService = new MedicoService();
    private static final NotificacaoService notificacaoService = new NotificacaoService();
    private static final ConsultaService consultaService = new ConsultaService(notificacaoService);
    private static final PrescricaoService prescricaoService = new PrescricaoService();
    private static final CatalogoService catalogoService = new CatalogoService();
    private static final PagamentoService pagamentoService = new PagamentoService();

    public static void main(String[] args) {
        while (true) {
            consultaService.verificarNotificacoes();
            exibirMenuPrincipal();
            executarOpcao(lerOpcao());
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n=== Sistema Clínica Médica ===");
        System.out.println("1. Cadastrar Paciente");
        System.out.println("2. Cadastrar Médico");
        System.out.println("3. Agendar Consulta");
        System.out.println("4. Listar Consultas");
        System.out.println("5. Ver Notificações");
        System.out.println("6. Realizar Pagamento");
        System.out.println("7. Sair");
        System.out.print("Escolha: ");
    }

    private static int lerOpcao() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida! Digite um número: ");
            }
        }
    }

    private static void executarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarPaciente();
            case 2 -> cadastrarMedico();
            case 3 -> agendarConsulta();
            case 4 -> listarConsultas();
            case 5 -> verNotificacoes();
            case 6 -> realizarPagamento();
            case 7 -> System.exit(0);
            default -> System.out.println("Opção inválida!");
        }
    }

    private static void cadastrarPaciente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            if (!ValidacaoUtil.validarNome(nome)) {
                throw new IllegalArgumentException("Nome deve conter apenas letras e ter pelo menos 3 caracteres!");
            }

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            if (!ValidacaoUtil.validarCPF(cpf)) {
                throw new IllegalArgumentException("CPF inválido!");
            }
            cpf = ValidacaoUtil.formatarCPF(cpf);

            System.out.print("Data Nascimento (AAAA-MM-DD): ");
            LocalDate dataNasc = LocalDate.parse(scanner.nextLine());
            
            Paciente paciente = new Paciente(nome, cpf, dataNasc);
            pacienteService.cadastrar(paciente);
            System.out.println("Paciente cadastrado com sucesso!");
            
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void cadastrarMedico() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            if (!ValidacaoUtil.validarNome(nome)) {
                throw new IllegalArgumentException("Nome inválido!");
            }

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            if (!ValidacaoUtil.validarCPF(cpf)) {
                throw new IllegalArgumentException("CPF inválido!");
            }
            cpf = ValidacaoUtil.formatarCPF(cpf); 

            System.out.print("CRM (Formato 12345/UF): ");
            String crm = scanner.nextLine();
            crm = ValidacaoUtil.formatarCRM(crm); 
            
            if (!ValidacaoUtil.validarCRM(crm)) {
                throw new IllegalArgumentException("CRM inválido!");
            }

            System.out.print("Especialidade: ");
            String especialidade = scanner.nextLine();
            
            System.out.print("Data Nascimento (AAAA-MM-DD): ");
            LocalDate dataNasc = LocalDate.parse(scanner.nextLine());
            
            Medico medico = new Medico(nome, cpf, dataNasc, crm, especialidade);
            medicoService.cadastrar(medico);
            System.out.println("Médico cadastrado com sucesso!");
            
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void agendarConsulta() {
        try {
            Consulta consulta = criarConsulta();
            prescreverItens(consulta);
            consultaService.agendarConsulta(consulta);
            System.out.println("Consulta agendada com sucesso!");
        } catch (IllegalArgumentException | PagamentoPendenteException | 
                 HorarioIndisponivelException | EspecialidadeInvalidaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static Consulta criarConsulta() {
    	System.out.print("CPF Paciente: ");
    	String cpfPaciente = ValidacaoUtil.formatarCPF(scanner.nextLine());
    	Paciente paciente = pacienteService.buscarPorCpf(cpfPaciente);
        
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado!");
        }
        
        System.out.print("CRM Médico: ");
        String crmInput = scanner.nextLine();
        String crmFormatado = ValidacaoUtil.formatarCRM(crmInput);
        
        if (!ValidacaoUtil.validarCRM(crmFormatado)) {
            throw new IllegalArgumentException("CRM inválido!");
        }
        
        Medico medico = medicoService.buscarPorCrm(crmFormatado);
        
        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado!");
        }
        
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());
        
        System.out.print("Horário (HH:MM): ");
        LocalTime horario = LocalTime.parse(scanner.nextLine());
        
        System.out.print("Duração (min): ");
        int duracao = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Especialidade Requerida: ");
        String especialidade = scanner.nextLine();
        if (especialidade.isBlank()) {
            throw new IllegalArgumentException("Especialidade não pode ser vazia!");
        }
        
        System.out.print("Valor: R$");
        double valor = Double.parseDouble(scanner.nextLine());

        return new Consulta(valor, data, horario, duracao, paciente, medico, especialidade);
    }

    private static void prescreverItens(Consulta consulta) {
        while (true) {
            System.out.println("\n=== Prescrição ===");
            System.out.println("1. Prescrever Exame");
            System.out.println("2. Prescrever Medicamento");
            System.out.println("3. Finalizar");
            System.out.print("Escolha: ");
            
            String input = scanner.nextLine();
            
            try {
                int escolha = Integer.parseInt(input);
                switch (escolha) {
                    case 1 -> prescreverExame(consulta.getPrescricao());
                    case 2 -> prescreverMedicamento(consulta.getPrescricao());
                    case 3 -> { return; }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números válidos!");
            }
        }
    }

    private static void prescreverExame(Prescricao prescricao) {
        List<Exame> exames = catalogoService.getExamesDisponiveis();
        System.out.println("\nExames Disponíveis:");
        
        for (int i = 0; i < exames.size(); i++) {
            System.out.println((i+1) + ". " + exames.get(i).getTipo() + " - R$" + exames.get(i).getValor());
        }
        
        try {
            System.out.print("Escolha o número do exame: ");
            int escolha = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (escolha >= 0 && escolha < exames.size()) {
                Exame exame = new Exame(
                    exames.get(escolha).getTipo(),
                    exames.get(escolha).getValor(),
                    LocalDate.now()
                );
                
                System.out.print("Data de Realização (AAAA-MM-DD): ");
                LocalDate data = LocalDate.parse(scanner.nextLine());
                exame.setDataRealizacao(data);
                prescricao.adicionarExame(exame);
                System.out.println("Exame prescrito com sucesso!");
            } else {
                System.out.println("Número inválido!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido!");
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números!");
        }
    }

    private static void prescreverMedicamento(Prescricao prescricao) {
        List<Medicamento> medicamentos = catalogoService.getMedicamentosDisponiveis();
        System.out.println("\nMedicamentos Disponíveis:");
        
        for (int i = 0; i < medicamentos.size(); i++) {
            Medicamento m = medicamentos.get(i);
            System.out.println((i+1) + ". " + m.getNome() + " - " + m.getDosagem() + " (" + m.getDuracaoTratamento() + " dias)");
        }
        
        try {
            System.out.print("Escolha o número do medicamento: ");
            int escolha = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (escolha >= 0 && escolha < medicamentos.size()) {
                Medicamento medicamento = medicamentos.get(escolha);
                prescricao.adicionarMedicamento(medicamento);
                System.out.println("Medicamento prescrito com sucesso!");
            } else {
                System.out.println("Número inválido!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números!");
        }
    }

    private static void listarConsultas() {
        System.out.println("\n=== Consultas Agendadas ===");
        consultaService.listarConsultas().forEach(consulta -> {
            System.out.println("\nPaciente: " + consulta.getPaciente().getNome());
            System.out.println("Médico: " + consulta.getMedico().getNome());
            System.out.println("Data: " + consulta.getData() + " às " + consulta.getHorarioInicio());
            System.out.println("Status: " + consulta.getStatus());
            prescricaoService.exibirPrescricao(consulta.getPrescricao());
            System.out.println("Valor: R$" + consulta.getValor());
        });
    }

    private static void verNotificacoes() {
        System.out.println("\n=== Notificações ===");
        List<Notificacao> naoLidas = notificacaoService.getNaoLidas();
        
        if (naoLidas.isEmpty()) {
            System.out.println("Nenhuma nova notificação.");
            return;
        }
        
        naoLidas.forEach(n -> System.out.println("• " + n.getMensagem()));
        notificacaoService.marcarComoLidas();
    }

    private static void realizarPagamento() {
        System.out.print("CPF Paciente: ");
        String cpf = scanner.nextLine();
        Paciente paciente = pacienteService.buscarPorCpf(cpf);
        
        if (paciente == null) {
            System.out.println("Paciente não encontrado!");
            return;
        }
        
        System.out.println("Procedimentos Pendentes:");
        paciente.getHistorico().stream()
            .filter(p -> !p.isPago())
            .forEach(p -> System.out.printf("%s - R$%.2f%n", p.getData(), p.getValor()));
        
        System.out.print("Data Procedimento (AAAA-MM-DD): ");
        String dataInput = scanner.nextLine();
        
        try {
            LocalDate data = LocalDate.parse(dataInput);
            Procedimento procedimento = paciente.getHistorico().stream()
                .filter(p -> p.getData().equals(data) && !p.isPago())
                .findFirst()
                .orElse(null);
            
            if (procedimento != null) {
                pagamentoService.realizarPagamento(procedimento);
                paciente.setPagamentoPendente(false);
                System.out.println("Pagamento realizado com sucesso!");
            } else {
                System.out.println("Nenhum procedimento pendente encontrado para esta data!");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido!");
        }
    }
}