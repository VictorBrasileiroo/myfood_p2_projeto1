package br.ufal.ic.myfood.exceptions;

public class PedidoParaEntregaNaoExisteException extends Exception {
    public PedidoParaEntregaNaoExisteException() {
        super("Nao existe pedido para entrega");
    }
}
