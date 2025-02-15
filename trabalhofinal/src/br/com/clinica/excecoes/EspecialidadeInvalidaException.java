package br.com.clinica.excecoes;

@SuppressWarnings("serial")
public class EspecialidadeInvalidaException extends Exception {
    public EspecialidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}