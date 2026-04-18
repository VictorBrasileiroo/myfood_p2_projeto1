package br.ufal.ic.myfood.exceptions;

public class ProdutoNaoPertenceEmpresaException extends Exception {
    public ProdutoNaoPertenceEmpresaException() {
        super("O produto nao pertence a essa empresa");
    }
}
