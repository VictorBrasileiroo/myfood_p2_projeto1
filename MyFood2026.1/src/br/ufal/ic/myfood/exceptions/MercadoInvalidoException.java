package br.ufal.ic.myfood.exceptions;

public class MercadoInvalidoException extends Exception {
    public MercadoInvalidoException() {
        super("Nao e um mercado valido");
    }
}
