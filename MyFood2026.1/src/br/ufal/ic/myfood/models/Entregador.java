package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;

public class Entregador extends Usuario {
    private static final long serialVersionUID = 1L;

    private String veiculo;
    private String placa;

    public Entregador() {
    }

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(id, nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    protected String getAtributoProprio(String atributo) throws AtributoInvalidoExc {
        switch (atributo) {
            case "veiculo": return getVeiculo();
            case "placa": return getPlaca();
            default: throw new AtributoInvalidoExc();
        }
    }

    @Override
    public boolean ehEntregador() {
        return true;
    }

    @Override
    public boolean temPlaca(String placa) {
        return getPlaca() != null && getPlaca().equals(placa);
    }

    @Override
    public String getTipo() {
        return "entregador";
    }
}
