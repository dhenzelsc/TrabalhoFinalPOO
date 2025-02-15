package br.com.clinica.servicos;

import br.com.clinica.entidades.*;
import br.com.clinica.excecoes.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaService {
    private final List<Consulta> consultas = new ArrayList<>();
    private final NotificacaoService notificacaoService;

    public ConsultaService(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    public void agendarConsulta(Consulta consulta) throws HorarioIndisponivelException, 
                                                          PagamentoPendenteException, 
                                                          EspecialidadeInvalidaException {
        validarAgendamento(consulta);
        consultas.add(consulta);
        consulta.getPaciente().adicionarProcedimento(consulta);
    }

    private void validarAgendamento(Consulta consulta) throws PagamentoPendenteException, 
                                                             EspecialidadeInvalidaException, 
                                                             HorarioIndisponivelException {
        if (consulta.getPaciente().temPagamentoPendente()) {
            throw new PagamentoPendenteException("Paciente com pagamentos pendentes!");
        }
        
        if (!consulta.getMedico().getEspecialidade().equalsIgnoreCase(consulta.getEspecialidadeRequerida())) {
            throw new EspecialidadeInvalidaException("Especialidade não corresponde!");
        }
        
        if (!medicoDisponivel(consulta.getMedico(), consulta.getData(), 
                    consulta.getHorarioInicio(), consulta.getDuracao())) {
            throw new HorarioIndisponivelException("Médico não disponível!");
        }
    }

    private boolean medicoDisponivel(Medico medico, LocalDate data, LocalTime inicio, int duracao) {
        LocalTime fim = inicio.plusMinutes(duracao);
        return consultas.stream()
            .filter(c -> c.getMedico().equals(medico) && c.getData().equals(data))
            .noneMatch(c -> {
                LocalTime cInicio = c.getHorarioInicio();
                LocalTime cFim = cInicio.plusMinutes(c.getDuracao());
                return inicio.isBefore(cFim) && fim.isAfter(cInicio);
            });
    }

    public void verificarNotificacoes() {
        consultas.forEach(consulta -> {
            verificarConsultaProxima(consulta);
            verificarExamesProximos(consulta);
        });
    }

    private void verificarConsultaProxima(Consulta consulta) {
        if (consulta.getData().isEqual(LocalDate.now().plusDays(1))) {
            String msg = String.format("Consulta com %s amanhã às %s", 
                consulta.getMedico().getNome(), consulta.getHorarioInicio());
            notificacaoService.adicionarNotificacao(msg);
        }
    }

    private void verificarExamesProximos(Consulta consulta) {
        consulta.getPrescricao().getExames().forEach(exame -> {
            if (exame.getDataRealizacao() != null && 
                exame.getDataRealizacao().isEqual(LocalDate.now().plusDays(1))) {
                String msg = String.format("Exame %s amanhã", exame.getTipo());
                notificacaoService.adicionarNotificacao(msg);
            }
        });
    }

    public List<Consulta> listarConsultas() { return consultas; }
}
