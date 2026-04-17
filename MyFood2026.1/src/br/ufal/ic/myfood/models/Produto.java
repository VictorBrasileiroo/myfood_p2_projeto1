package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int empresaId;
    private String nome;
    private double valor;
    private String categoria;

    public Produto() {}

    public Produto(int id, int empresaId, String nome, double valor, String categoria) {
        this.id = id;
        this.empresaId = empresaId;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
