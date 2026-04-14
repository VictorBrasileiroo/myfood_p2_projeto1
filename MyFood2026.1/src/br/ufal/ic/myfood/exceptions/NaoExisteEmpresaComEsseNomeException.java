package br.ufal.ic.myfood.exceptions;

public class NaoExisteEmpresaComEsseNomeException extends Exception {

    public NaoExisteEmpresaComEsseNomeException() {
        super("Nao existe empresa com esse nome");
    }
}