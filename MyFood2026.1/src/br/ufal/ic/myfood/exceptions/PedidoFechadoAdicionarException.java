package br.ufal.ic.myfood.exceptions;

public class PedidoFechadoAdicionarException extends Exception {
    public PedidoFechadoAdicionarException() {
        super("Nao e possivel adcionar produtos a um pedido fechado");
    }
}
