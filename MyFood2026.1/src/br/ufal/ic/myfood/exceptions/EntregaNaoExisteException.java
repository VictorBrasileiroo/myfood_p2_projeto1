package br.ufal.ic.myfood.exceptions;

public class EntregaNaoExisteException extends Exception {
    public EntregaNaoExisteException() {
        super("Nao existe entrega com esse id");
    }
}
