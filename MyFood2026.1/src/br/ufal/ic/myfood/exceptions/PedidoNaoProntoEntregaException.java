package br.ufal.ic.myfood.exceptions;

public class PedidoNaoProntoEntregaException extends Exception {
    public PedidoNaoProntoEntregaException() {
        super("Pedido nao esta pronto para entrega");
    }
}
