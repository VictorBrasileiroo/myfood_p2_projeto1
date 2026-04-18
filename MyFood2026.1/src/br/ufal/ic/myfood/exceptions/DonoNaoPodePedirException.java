package br.ufal.ic.myfood.exceptions;

public class DonoNaoPodePedirException extends Exception {
    public DonoNaoPodePedirException() {
        super("Dono de empresa nao pode fazer um pedido");
    }
}
