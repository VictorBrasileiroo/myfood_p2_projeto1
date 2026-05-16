package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;

public class DonoDeEmpresa extends Usuario {
    private static final long serialVersionUID = 1L;

    private String cpf;

    public DonoDeEmpresa() {}

    public DonoDeEmpresa(int id, String nome, String email, String senha, String endereco, String cpf) {
        super(id, nome, email, senha, endereco);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    protected String getAtributoProprio(String atributo) throws AtributoInvalidoExc {
        if ("cpf".equals(atributo)) return getCpf();
        throw new AtributoInvalidoExc();
    }

    @Override
    public boolean ehDonoDeEmpresa() {
        return true;
    }

    @Override
    public String getTipo() {
        return "dono";
    }
}
