package br.ufal.ic.myfood.exceptions;

public class PedidoNaoPreparandoException extends Exception {
    public PedidoNaoPreparandoException() {
        super("Nao e possivel liberar um produto que nao esta sendo preparado");
    }
}
