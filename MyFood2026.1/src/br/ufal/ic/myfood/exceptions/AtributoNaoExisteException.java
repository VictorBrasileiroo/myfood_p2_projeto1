package br.ufal.ic.myfood.exceptions;

public class AtributoNaoExisteException extends Exception {
    public AtributoNaoExisteException() {
        super("Atributo nao existe");
    }
}
