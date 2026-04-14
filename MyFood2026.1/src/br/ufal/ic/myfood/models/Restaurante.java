package br.ufal.ic.myfood.models;

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
    public String getTipo() {
        return "restaurante";
    }
}