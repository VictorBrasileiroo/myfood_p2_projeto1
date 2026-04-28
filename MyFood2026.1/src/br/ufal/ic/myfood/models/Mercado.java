package br.ufal.ic.myfood.models;

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
    public String getTipo() {
        return "mercado";
    }
}
