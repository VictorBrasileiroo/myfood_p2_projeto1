package br.ufal.ic.myfood.repositories;

import br.ufal.ic.myfood.models.Produto;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {

    private static final String ARQUIVO_PRODUTOS = "dados/produtos.xml";

    private List<Produto> produtos;
    private int proximoId;

    public ProdutoRepository() {
        this.produtos = new ArrayList<>();
        this.proximoId = 1;
    }

    public void salvarDados() throws IOException {
        File dir = new File("dados");
        if (!dir.exists()) dir.mkdirs();
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ARQUIVO_PRODUTOS))) {
            encoder.writeObject(this.proximoId);
            encoder.writeObject(this.produtos);
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados() throws IOException {
        File arquivo = new File(ARQUIVO_PRODUTOS);
        if (!arquivo.exists()) return;
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(arquivo))) {
            this.proximoId = (int) decoder.readObject();
            this.produtos = (List<Produto>) decoder.readObject();
        }
    }

    public void salvar(Produto produto) {
        this.produtos.add(produto);
    }

    public Produto buscarPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public Produto buscarPorNomeEEmpresa(String nome, int empresaId) {
        for (Produto p : produtos) {
            if (p.getEmpresaId() == empresaId && p.getNome().equals(nome)) return p;
        }
        return null;
    }

    public List<Produto> listarPorEmpresa(int empresaId) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            if (p.getEmpresaId() == empresaId) resultado.add(p);
        }
        return resultado;
    }

    public int gerarId() {
        return this.proximoId++;
    }

    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}
