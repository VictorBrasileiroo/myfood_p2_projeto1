package br.ufal.ic.myfood.exceptions;

public class UsuarioNaoEntregadorException extends Exception {
    public UsuarioNaoEntregadorException() {
        super("Usuario nao e um entregador");
    }
}
