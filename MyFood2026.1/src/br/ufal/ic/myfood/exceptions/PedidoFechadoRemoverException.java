package br.ufal.ic.myfood.exceptions;

public class PedidoFechadoRemoverException extends Exception {
    public PedidoFechadoRemoverException() {
        super("Nao e possivel remover produtos de um pedido fechado");
    }
}
