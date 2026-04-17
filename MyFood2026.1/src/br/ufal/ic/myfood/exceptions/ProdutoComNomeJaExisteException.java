package br.ufal.ic.myfood.exceptions;

public class ProdutoComNomeJaExisteException extends Exception {
    public ProdutoComNomeJaExisteException() {
        super("Ja existe um produto com esse nome para essa empresa");
    }
}
