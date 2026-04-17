package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoCadastradoException extends Exception {
    public ProdutoNaoCadastradoException() {
        super("Produto nao cadastrado");
    }
}
