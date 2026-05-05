package br.ufal.ic.myfood.exceptions;

public class EntregadorSemEmpresaException extends Exception {
    public EntregadorSemEmpresaException() {
        super("Entregador nao estar em nenhuma empresa.");
    }
}
