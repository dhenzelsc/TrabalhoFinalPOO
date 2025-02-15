package br.com.clinica.servicos;

import br.com.clinica.entidades.Procedimento;

public class PagamentoService {
    public void realizarPagamento(Procedimento procedimento) {
        procedimento.marcarComoPago();
    }
}