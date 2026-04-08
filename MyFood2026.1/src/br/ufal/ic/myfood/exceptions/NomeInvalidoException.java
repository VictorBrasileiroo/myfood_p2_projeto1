package br.ufal.ic.myfood.exceptions;

public class NomeInvalidoException extends RuntimeException {
    public NomeInvalidoException() {
        super("Nome inválido");
    }
}
