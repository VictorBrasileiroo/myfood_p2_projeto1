package br.ufal.ic.myfood.exceptions;

public class SenhaInvalidoException extends Exception {
    public SenhaInvalidoException() {
        super("Senha invalido");
    }
}
