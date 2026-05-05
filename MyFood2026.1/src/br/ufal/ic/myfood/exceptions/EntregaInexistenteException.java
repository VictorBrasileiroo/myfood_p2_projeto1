package br.ufal.ic.myfood.exceptions;

public class EntregaInexistenteException extends Exception {
    public EntregaInexistenteException() {
        super("Nao existe nada para ser entregue com esse id");
    }
}
