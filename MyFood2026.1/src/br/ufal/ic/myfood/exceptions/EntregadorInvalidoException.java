package br.ufal.ic.myfood.exceptions;

public class EntregadorInvalidoException extends Exception {
    public EntregadorInvalidoException() {
        super("Nao e um entregador valido");
    }
}
