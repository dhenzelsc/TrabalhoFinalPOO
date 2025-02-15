package br.com.clinica.excecoes;

@SuppressWarnings("serial")
public class HorarioIndisponivelException extends Exception {
    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}