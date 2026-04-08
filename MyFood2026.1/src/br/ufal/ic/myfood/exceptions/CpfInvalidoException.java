package br.ufal.ic.myfood.exceptions;

public class CpfInvalidoException extends Exception {
    public CpfInvalidoException() {
        super("CPF invalido");
    }
}
