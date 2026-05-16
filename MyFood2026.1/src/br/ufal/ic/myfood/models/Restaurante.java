package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;

public class Restaurante extends Empresa {
    private static final long serialVersionUID = 1L;

    private String tipoCozinha;

    public Restaurante() {
    }

    public Restaurante(int id, String nome, String endereco, int donoId, String tipoCozinha) {
        super(id, nome, endereco, donoId);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    @Override
    protected String getAtributoProprio(String atributo) throws AtributoInvalidoExc {
        if ("tipoCozinha".equals(atributo)) return getTipoCozinha();
        throw new AtributoInvalidoExc();
    }

    @Override
    public String getTipo() {
        return "restaurante";
    }
}
