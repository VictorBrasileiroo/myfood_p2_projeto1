package br.ufal.ic.myfood.exceptions;

public class EntregadorEmEntregaException extends Exception {
    public EntregadorEmEntregaException() {
        super("Entregador ainda em entrega");
    }
}
