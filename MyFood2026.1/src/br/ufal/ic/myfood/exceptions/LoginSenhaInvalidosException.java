package br.ufal.ic.myfood.exceptions;

public class LoginSenhaInvalidosException extends Exception {
    public LoginSenhaInvalidosException() {
        super("Login ou senha invalidos");
    }
}
