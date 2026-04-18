package br.ufal.ic.myfood.exceptions;

public class PedidoNaoEncontradoException extends Exception {
    public PedidoNaoEncontradoException() {
        super("Pedido nao encontrado");
    }
}
