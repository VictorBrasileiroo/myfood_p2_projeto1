package br.ufal.ic.myfood.exceptions;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() {
        super("Email invalido");
    }
}
