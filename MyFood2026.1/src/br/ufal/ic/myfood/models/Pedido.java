package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numero;
    private int clienteId;
    private int empresaId;
    private String estado;
    private List<Integer> produtosIds;

    public Pedido() {
        this.produtosIds = new ArrayList<>();
    }

    public Pedido(int numero, int clienteId, int empresaId) {
        this.numero = numero;
        this.clienteId = clienteId;
        this.empresaId = empresaId;
        this.estado = "aberto";
        this.produtosIds = new ArrayList<>();
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getEmpresaId() { return empresaId; }
    public void setEmpresaId(int empresaId) { this.empresaId = empresaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Integer> getProdutosIds() { return produtosIds; }
    public void setProdutosIds(List<Integer> produtosIds) { this.produtosIds = produtosIds; }
}
