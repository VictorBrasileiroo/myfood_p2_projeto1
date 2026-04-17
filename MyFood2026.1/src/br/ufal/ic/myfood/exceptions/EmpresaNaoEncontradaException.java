package br.ufal.ic.myfood.exceptions;

public class EmpresaNaoEncontradaException extends Exception {
    public EmpresaNaoEncontradaException() {
        super("Empresa nao encontrada");
    }
}
