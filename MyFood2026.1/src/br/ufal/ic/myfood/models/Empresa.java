package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String endereco;
    private int donoId;
    private List<Integer> entregadoresIds;

    public Empresa() {
        this.entregadoresIds = new ArrayList<>();
    }

    public Empresa(int id, String nome, String endereco, int donoId) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.donoId = donoId;
        this.entregadoresIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getDonoId() {
        return donoId;
    }

    public void setDonoId(int donoId) {
        this.donoId = donoId;
    }

    public List<Integer> getEntregadoresIds() {
        if (entregadoresIds == null) entregadoresIds = new ArrayList<>();
        return entregadoresIds;
    }

    public void setEntregadoresIds(List<Integer> entregadoresIds) {
        this.entregadoresIds = entregadoresIds;
    }

    public abstract String getTipo();
}
