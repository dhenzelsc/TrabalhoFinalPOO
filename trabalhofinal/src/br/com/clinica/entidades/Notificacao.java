package br.com.clinica.entidades;

import java.time.LocalDateTime;

public class Notificacao {
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;

    public Notificacao(String mensagem) {
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.lida = false;
    }

    // Getters e Setters
    public String getMensagem() { return mensagem; }
    public boolean isLida() { return lida; }
    public void marcarComoLida() { this.lida = true; }
}