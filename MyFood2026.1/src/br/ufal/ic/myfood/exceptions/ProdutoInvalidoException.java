package br.ufal.ic.myfood.exceptions;

public class ProdutoInvalidoException extends Exception {
    public ProdutoInvalidoException() {
        super("Produto invalido");
    }
}
