package br.ufal.ic.myfood.exceptions;

public class PedidoNaoExisteAbertoException extends Exception {
    public PedidoNaoExisteAbertoException() {
        super("Nao existe pedido em aberto");
    }
}
