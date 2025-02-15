package br.com.clinica.servicos;

import br.com.clinica.entidades.Notificacao;
import java.util.ArrayList;
import java.util.List;

public class NotificacaoService {
    private List<Notificacao> notificacoes = new ArrayList<>();

    public void adicionarNotificacao(String mensagem) {
        notificacoes.add(new Notificacao(mensagem));
    }

    public List<Notificacao> getNaoLidas() {
        return notificacoes.stream()
            .filter(n -> !n.isLida())
            .toList();
    }

    public void marcarComoLidas() {
        notificacoes.forEach(Notificacao::marcarComoLida);
    }
}