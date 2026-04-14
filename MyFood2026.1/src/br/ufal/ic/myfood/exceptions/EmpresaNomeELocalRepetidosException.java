package br.ufal.ic.myfood.exceptions;

public class EmpresaNomeELocalRepetidosException extends Exception {

    public EmpresaNomeELocalRepetidosException() {
        super("Proibido cadastrar duas empresas com o mesmo nome e local");
    }
}