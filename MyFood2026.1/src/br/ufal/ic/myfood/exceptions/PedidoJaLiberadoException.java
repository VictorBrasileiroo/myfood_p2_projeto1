package br.ufal.ic.myfood.exceptions;

public class PedidoJaLiberadoException extends Exception {
    public PedidoJaLiberadoException() {
        super("Pedido ja liberado");
    }
}
