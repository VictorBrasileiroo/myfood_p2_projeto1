package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.AtributoInvalidoExc;

public class Mercado extends Empresa {
    private static final long serialVersionUID = 1L;

    private String abre;
    private String fecha;
    private String tipoMercado;

    public Mercado() {
    }

    public Mercado(int id, String nome, String endereco, int donoId, String abre, String fecha, String tipoMercado) {
        super(id, nome, endereco, donoId);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getAbre() {
        return abre;
    }

    public void setAbre(String abre) {
        this.abre = abre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMercado() {
        return tipoMercado;
    }

    public void setTipoMercado(String tipoMercado) {
        this.tipoMercado = tipoMercado;
    }

    @Override
    protected String getAtributoProprio(String atributo) throws AtributoInvalidoExc {
        switch (atributo) {
            case "abre": return getAbre();
            case "fecha": return getFecha();
            case "tipoMercado": return getTipoMercado();
            default: throw new AtributoInvalidoExc();
        }
    }

    @Override
    public void alterarFuncionamento(String abre, String fecha) {
        setAbre(abre);
        setFecha(fecha);
    }

    @Override
    public String getTipo() {
        return "mercado";
    }
}
