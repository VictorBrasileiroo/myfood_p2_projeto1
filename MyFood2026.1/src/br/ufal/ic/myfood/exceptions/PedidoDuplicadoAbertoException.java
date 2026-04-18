package br.ufal.ic.myfood.exceptions;

public class PedidoDuplicadoAbertoException extends Exception {
    public PedidoDuplicadoAbertoException() {
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }
}
