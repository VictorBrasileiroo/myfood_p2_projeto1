package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado");
    }
}
