package br.com.clinica.excecoes;

@SuppressWarnings("serial")
public class PagamentoPendenteException extends Exception {
    public PagamentoPendenteException(String mensagem) {
        super(mensagem);
    }
}