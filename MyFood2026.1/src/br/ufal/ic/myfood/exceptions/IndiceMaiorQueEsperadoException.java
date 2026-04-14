package br.ufal.ic.myfood.exceptions;

public class IndiceMaiorQueEsperadoException extends Exception {

    public IndiceMaiorQueEsperadoException() {
        super("Indice maior que o esperado");
    }
}